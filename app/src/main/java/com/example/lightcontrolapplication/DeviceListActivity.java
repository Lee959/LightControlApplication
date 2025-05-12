package com.example.lightcontrolapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.example.lightcontrolapplication.owon.sdk.util.Constants;
import com.example.lightcontrolapplication.owon.sdk.util.DeviceMessagesManager;
import com.example.lightcontrolapplication.owon.sdk.util.SocketMessageListener;

public class DeviceListActivity extends AppCompatActivity {

    private ListView deviceListView;
    private DeviceMessagesManager deviceManager;
    private List<DeviceInfo> deviceList = new ArrayList<>();
    private ArrayAdapter<DeviceInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        deviceListView = findViewById(R.id.device_list_view);
        deviceManager = new DeviceMessagesManager();

        // Create adapter for device list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        deviceListView.setAdapter(adapter);

        // Set item click listener
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceInfo selectedDevice = deviceList.get(position);

                // Make sure we only select light devices
                if (selectedDevice.getDeviceType() == Constants.LIGHT_601 ||
                        selectedDevice.getDeviceType() == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB) {

                    // Create intent to pass to MainActivity
                    Intent intent = new Intent(DeviceListActivity.this, MainActivity.class);
                    intent.putExtra("DEVICE_IEEE", selectedDevice.getIeee());
                    intent.putExtra("DEVICE_EP", selectedDevice.getEp());
                    intent.putExtra("DEVICE_NAME", selectedDevice.getName());
                    startActivity(intent);
                } else {
                    Toast.makeText(DeviceListActivity.this,
                            "Please select a light device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register listener for device list updates
        registerDeviceListListener();

        // Query device list
        queryDeviceList();
    }

    /**
     * Query the list of devices
     */
    private void queryDeviceList() {
        try {
            deviceManager.GetEpList();
        } catch (Exception e) {
            Toast.makeText(this, "Error querying device list: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Register listener for device list updates
     */
    private void registerDeviceListListener() {
        SocketMessageListener listener = new SocketMessageListener() {
            @Override
            public void getMessage(int commandID, Object bean) {
                if (commandID == Constants.ZigBeeGetEPList) {
                    updateDeviceList(bean);
                }
            }
        };

        // Register the listener with your manager
        // This implementation depends on how your SDK is set up
        // deviceManager.registerListener(listener);
    }

    /**
     * Update the device list when new data is received
     * @param bean Response bean with device list data
     */
    private void updateDeviceList(final Object bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Cast bean to EPListBean as per your SDK
                    EPListBean epListBean = (EPListBean) bean;

                    // Clear current list
                    deviceList.clear();

                    // Add only light devices to the list
                    for (DeviceInfo device : epListBean.getDevices()) {
                        if (device.getDeviceType() == Constants.LIGHT_601 ||
                                device.getDeviceType() == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB) {
                            deviceList.add(device);
                        }
                    }

                    // Update adapter
                    adapter.notifyDataSetChanged();

                    if (deviceList.isEmpty()) {
                        Toast.makeText(DeviceListActivity.this,
                                "No light devices found", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(DeviceListActivity.this,
                            "Error updating device list: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}