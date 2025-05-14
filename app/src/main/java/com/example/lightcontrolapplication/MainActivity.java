package com.example.lightcontrolapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lightcontrolapplication.owon.sdk.util.Constants;
import com.example.lightcontrolapplication.owon.sdk.util.DeviceMessagesManager;
import com.example.lightcontrolapplication.owon.sdk.util.SocketMessageListener;

public class MainActivity extends AppCompatActivity implements SocketMessageListener {

    private static final String TAG = "MainActivity";

    // UI components
    private TextView tvLightStatus;
    private TextView tvConnectionStatus;
    private ImageView ivLightBulb;
    private Switch switchLight;
    private Button btnRefresh;

    // Device management
    private DeviceMessagesManager deviceManager;
    private EPListBean lightDevice;
    private boolean isLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initViews();

        // Initialize Device Manager
        deviceManager = DeviceMessagesManager.getInstance();

        // Register this activity as a message listener
        deviceManager.registerMessageListener(this);

        // Set up click listeners
        setupListeners();

        // Connect to device (simulate connection)
        connectToDevice();
    }

    /**
     * Connect to the device and immediately update the UI
     */
    private void connectToDevice() {
        // In a real app, this would be where you establish connection
        // For demo, we'll simulate a connection with a slight delay to mimic real-world behavior

        // Show connecting status
        tvConnectionStatus.setText("正在连接设备...");
        tvConnectionStatus.setTextColor(getResources().getColor(R.color.red));

        new android.os.Handler().postDelayed(() -> {
            // Initialize with a simulated light device (only LIGHT_601 type - switch only)
            lightDevice = new EPListBean("简易灯", "light001", Constants.LIGHT_601, true);

            // Update connection status immediately
            updateConnectionStatus();

            // Get initial device state and update UI immediately
            refreshDeviceState();

            // Show toast to indicate successful connection
            Toast.makeText(this, "设备连接成功", Toast.LENGTH_SHORT).show();
        }, 1000); // 1-second delay to simulate connection time
    }

    private void initViews() {
        tvLightStatus = findViewById(R.id.tvLightStatus);
        tvConnectionStatus = findViewById(R.id.tvConnectionStatus);
        ivLightBulb = findViewById(R.id.ivLightBulb);
        switchLight = findViewById(R.id.switchLight);
        btnRefresh = findViewById(R.id.btnRefresh);
    }

    private void setupListeners() {
        // Light switch change listener
        switchLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {  // Only respond to user interactions, not programmatic changes
                controlLight(isChecked);
            }
        });

        // Refresh button click listener
        btnRefresh.setOnClickListener(v -> refreshDeviceState());
    }

    private void controlLight(boolean turnOn) {
        if (lightDevice != null && lightDevice.isLinkStatus()) {
            // Verify this is a LIGHT_601 type device (switch-only light)
            if (lightDevice.getDeviceType() == Constants.LIGHT_601) {
                // In a real app, you would call the actual method to control the light
                // deviceManager.setUpSwitch(lightDevice, turnOn);

                // For demonstration, we'll simulate the response
                simulateLightControl(turnOn);

                Log.d(TAG, "Light control command sent: " + (turnOn ? "ON" : "OFF"));
            } else {
                Toast.makeText(this, "该应用仅支持开关灯控制", Toast.LENGTH_SHORT).show();
                // Reset switch to match actual state if device is not supported
                switchLight.setChecked(isLightOn);
            }
        } else {
            Toast.makeText(this, "设备离线或未选择设备", Toast.LENGTH_SHORT).show();
            // Reset switch to match actual state if device is offline
            switchLight.setChecked(isLightOn);
        }
    }

    private void refreshDeviceState() {
        if (lightDevice != null) {
            // In a real app, you would call the actual method to get device state
            // deviceManager.getDeviceState(lightDevice, 0);

            // For demonstration, we'll simulate the response
            simulateDeviceStateResponse();

            Log.d(TAG, "Refresh device state command sent");
        }
    }

    private void updateLightUI(boolean isOn) {
        isLightOn = isOn;

        // Update switch state (without triggering the listener)
        switchLight.setOnCheckedChangeListener(null);
        switchLight.setChecked(isOn);
        switchLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                controlLight(isChecked);
            }
        });

        // Update status text
        tvLightStatus.setText("当前状态: " + (isOn ? "开启" : "关闭"));

        // Update light bulb image with animation
        fadeImageTransition(ivLightBulb, isOn ? R.drawable.light_on : R.drawable.light_off);
    }

    /**
     * Animate the transition between light states
     */
    private void fadeImageTransition(final ImageView imageView, final int newImageResource) {
        // Fade out animation
        imageView.animate()
                .alpha(0f)
                .setDuration(150)
                .withEndAction(() -> {
                    // Change the image resource when completely faded out
                    imageView.setImageResource(newImageResource);

                    // Fade in animation
                    imageView.animate()
                            .alpha(1f)
                            .setDuration(150)
                            .start();
                })
                .start();
    }

    private void updateConnectionStatus() {
        if (lightDevice != null) {
            boolean isOnline = lightDevice.isLinkStatus();
            tvConnectionStatus.setText(isOnline ? "设备在线" : "设备离线");
            tvConnectionStatus.setTextColor(isOnline ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red));
        } else {
            tvConnectionStatus.setText("未连接设备");
            tvConnectionStatus.setTextColor(getResources().getColor(R.color.red));
        }
    }

    // Implementation of SocketMessageListener interface
    @Override
    public void getMessage(int commandID, Object bean) {
        // Handle different command IDs
        switch (commandID) {
            case Constants.UpdateSwitchgear:
                // Handle light switch status update
                if (bean instanceof z_UpdateSwitchgearBean) {
                    z_UpdateSwitchgearBean switchBean = (z_UpdateSwitchgearBean) bean;
                    boolean isOn = "on".equals(switchBean.getSwitchgear());

                    // Update UI on the main thread immediately
                    runOnUiThread(() -> {
                        updateLightUI(isOn);
                        Log.d(TAG, "Light status updated immediately: " + (isOn ? "ON" : "OFF"));
                    });
                }
                break;

            case Constants.SmartLightSetupSwitchgear:
                // Handle response from setting up light switch
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "灯光控制命令已执行", Toast.LENGTH_SHORT).show());
                break;

            case Constants.UpdateLight:
                // Handle physical control response
                if (bean instanceof z_UpdateSwitchgearBean) {
                    z_UpdateSwitchgearBean switchBean = (z_UpdateSwitchgearBean) bean;
                    boolean isOn = "on".equals(switchBean.getSwitchgear());

                    // Update UI on the main thread immediately
                    runOnUiThread(() -> {
                        updateLightUI(isOn);
                        Toast.makeText(MainActivity.this, "灯光状态已通过物理开关更新", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Light physically controlled: " + (isOn ? "ON" : "OFF"));
                    });
                }
                break;

            default:
                Log.d(TAG, "Received unhandled command ID: " + commandID);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister the message listener to prevent memory leaks
        if (deviceManager != null) {
            deviceManager.unregisterMessageListener(this);
        }
        super.onDestroy();
    }

    // Simulation methods for testing without backend

    private void simulateLightControl(boolean turnOn) {
        // Create a response bean
        z_UpdateSwitchgearBean responseBean = new z_UpdateSwitchgearBean();
        responseBean.setSwitchgear(turnOn ? "on" : "off");
        responseBean.setIeee(lightDevice.getId());
        responseBean.setEp(1);

        // First simulate command sent successfully
        getMessage(Constants.SmartLightSetupSwitchgear, null);

        // Then simulate updated state after brief delay
        new android.os.Handler().postDelayed(() -> {
            getMessage(Constants.UpdateSwitchgear, responseBean);
        }, 500);
    }

    private void simulateDeviceStateResponse() {
        // Create a response bean with current state
        z_UpdateSwitchgearBean responseBean = new z_UpdateSwitchgearBean();
        responseBean.setSwitchgear(isLightOn ? "on" : "off");
        responseBean.setIeee(lightDevice.getId());
        responseBean.setEp(1);

        // Send simulated response
        getMessage(Constants.UpdateSwitchgear, responseBean);
    }
}