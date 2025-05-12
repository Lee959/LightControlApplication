package com.example.lightcontrolapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lightcontrolapplication.owon.sdk.util.Constants;
import com.example.lightcontrolapplication.owon.sdk.util.DeviceMessagesManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // UI components
    private Switch lightSwitch;
    private TextView statusTextView;
    private ImageView lightImageView;
    private Button refreshButton;

    // Device data
    private String deviceIeee;
    private int deviceEp;

    // Device manager
    private DeviceMessagesManager deviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        lightSwitch = findViewById(R.id.light_switch);
        statusTextView = findViewById(R.id.status_text);
        lightImageView = findViewById(R.id.light_image);
        refreshButton = findViewById(R.id.refresh_button);

        // Initialize device manager
        initDeviceManager();

        // Set up switch listener
        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (deviceIeee != null && !deviceIeee.isEmpty()) {
                    controlLight(isChecked);
                } else {
                    Toast.makeText(MainActivity.this, "没有连接设备", Toast.LENGTH_SHORT).show();
                    // Reset the switch if no device is connected
                    lightSwitch.setChecked(!isChecked);
                }
            }
        });

        // Set up refresh button
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceList();
                Toast.makeText(MainActivity.this, "正在刷新设备列表...", Toast.LENGTH_SHORT).show();
            }
        });

        // Initial device query
        getDeviceList();
    }

    private void initDeviceManager() {
        deviceManager = new DeviceMessagesManager();

        // Set up message listener
        deviceManager.setSocketMessageListener(new SocketMessageListener() {
            @Override
            public void getMessage(int commandID, Object bean) {
                runOnUiThread(() -> {
                    handleMessageCallback(commandID, bean);
                });
            }
        });
    }

    private void handleMessageCallback(int commandID, Object bean) {
        switch (commandID) {
            case Constants.ZigBeeGetEPList:
                // Handle device list
                handleDeviceList(bean);
                break;

            case Constants.UpdateSwitchgear:
                // Handle switch state update (from query or control)
                handleSwitchUpdate(bean);
                break;

            case Constants.UpdateLight:
                // Handle switch state update (from physical control)
                handleSwitchUpdate(bean);
                break;

            case Constants.SmartLightSetupSwitchgear:
                // Handle setup switchgear result
                handleSetupResult(bean);
                break;

            default:
                Log.d(TAG, "Unknown command ID: " + commandID);
                break;
        }
    }

    private void handleDeviceList(Object bean) {
        if (bean instanceof ArrayList) {
            ArrayList<?> deviceList = (ArrayList<?>) bean;

            // Find the first light device
            for (Object device : deviceList) {
                if (device instanceof DeviceInfoBean) {
                    DeviceInfoBean deviceInfo = (DeviceInfoBean) device;

                    // Check if the device is a light
                    if (deviceInfo.getType() == Constants.LIGHT_601 ||
                            deviceInfo.getType() == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB) {

                        // Save device information
                        deviceIeee = deviceInfo.getIeee();
                        deviceEp = deviceInfo.getEp();

                        // Update UI
                        statusTextView.setText("已连接设备: " + deviceInfo.getName());

                        // Query current light state
                        queryLightState();
                        return;
                    }
                }
            }

            // No light device found
            statusTextView.setText("No light devices found");
        }
    }

    private void handleSwitchUpdate(Object bean) {
        if (bean instanceof z_UpdateSwitchgearBean) {
            z_UpdateSwitchgearBean switchBean = (z_UpdateSwitchgearBean) bean;

            // Update switch state based on returned value
            boolean isOn = "on".equals(switchBean.getSwitchgear());

            // Update UI
            lightSwitch.setChecked(isOn);
            statusTextView.setText("灯状态: " + (isOn ? "开启" : "关闭"));

            // Update light image
            updateLightImage(isOn);
        }
    }

    private void updateLightImage(boolean isOn) {
        if (isOn) {
            lightImageView.setImageResource(R.drawable.light_on);
        } else {
            lightImageView.setImageResource(R.drawable.light_on);
        }
    }

    private void handleSetupResult(Object bean) {
        // Handle the result of setting up the switchgear
        if (bean instanceof z_UpdateSwitchgearBean) {
            z_UpdateSwitchgearBean resultBean = (z_UpdateSwitchgearBean) bean;

            boolean success = resultBean != null;

            if (success) {
                Log.d(TAG, "Light control successful");

                // Query the current state to ensure UI reflects actual state
                queryLightState();
            } else {
                Log.e(TAG, "Light control failed");
                Toast.makeText(this, "Failed to control light", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDeviceList() {
        try {
            deviceManager.getDeviceList();
        } catch (Exception e) {
            Log.e(TAG, "Error getting device list: " + e.getMessage());
            statusTextView.setText("Error getting device list");
        }
    }

    private void queryLightState() {
        try {
            if (deviceIeee != null && !deviceIeee.isEmpty()) {
                deviceManager.queryState(deviceIeee, deviceEp);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying light state: " + e.getMessage());
        }
    }

    private void controlLight(boolean turnOn) {
        try {
            // Create device info bean
            DeviceInfoBean infoBean = new DeviceInfoBean();
            infoBean.setIeee(deviceIeee);
            infoBean.setEp(deviceEp);

            // Call the device manager to control the light
            deviceManager.setUpSwitch(infoBean, turnOn);

            Log.d(TAG, "Sending command to turn " + (turnOn ? "ON" : "OFF") + " light");
            statusTextView.setText("Setting light to " + (turnOn ? "ON" : "OFF") + "...");
        } catch (Exception e) {
            Log.e(TAG, "Error controlling light: " + e.getMessage());
            Toast.makeText(this, "Error controlling light", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up resources
        deviceManager = null;
    }
}