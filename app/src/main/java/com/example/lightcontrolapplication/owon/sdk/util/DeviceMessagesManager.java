package com.example.lightcontrolapplication.owon.sdk.util;

import android.util.Log;

import com.example.lightcontrolapplication.EPListBean;
import com.example.lightcontrolapplication.z_UpdateSwitchgearBean;

import java.util.ArrayList;
import java.util.List;


public class DeviceMessagesManager {

    private static DeviceMessagesManager instance;
    private List<SocketMessageListener> listeners;

    private DeviceMessagesManager() {
        listeners = new ArrayList<>();
    }

    public static synchronized DeviceMessagesManager getInstance() {
        if (instance == null) {
            instance = new DeviceMessagesManager();
        }
        return instance;
    }

    public void registerMessageListener(SocketMessageListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregisterMessageListener(SocketMessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Set up light switch - only for LIGHT_601 devices
     *
     * @param infobean    Device entity
     * @param switchgear  Switch status (true for on, false for off)
     */
    public void setUpSwitch(EPListBean infobean, boolean switchgear) {
        if (infobean.getDeviceType() == Constants.LIGHT_601) {
            z_UpdateSwitchgearBean responseBean = new z_UpdateSwitchgearBean();
            responseBean.setSwitchgear(switchgear ? "on" : "off");
            responseBean.setIeee(infobean.getId());
            responseBean.setEp(1); // Set Default Value ep is 1 for this example

            for (SocketMessageListener listener : listeners) {
                listener.getMessage(Constants.SmartLightSetupSwitchgear, null);
            }

            // Simulate network delay
            new android.os.Handler().postDelayed(() -> {
                // Then send the updated status
                for (SocketMessageListener listener : listeners) {
                    listener.getMessage(Constants.UpdateSwitchgear, responseBean);
                }
            }, 500);
        } else {
            // Log error for unsupported device type
            Log.e("DeviceMessagesManager", "Attempted to control unsupported device type: " + infobean.getDeviceType());

        }
    }

    /**
     * Get the state of a device.
     *
     * @param device The device to query.
     * @param cache  Use cache flag (0 for no cache, 1 for using cache).
     */
    public void getDeviceState(EPListBean device, int cache) {
        if (device.getDeviceType() == Constants.LIGHT_601) {
            z_UpdateSwitchgearBean responseBean = new z_UpdateSwitchgearBean();

            // For Testing: Randomly set the initial state for demo purposes
            boolean isOn = Math.random() > 0.5;
            responseBean.setSwitchgear(isOn ? "on" : "off");
            responseBean.setIeee(device.getId());
            responseBean.setEp(1);

            for (SocketMessageListener listener : listeners) {
                listener.getMessage(Constants.UpdateSwitchgear, responseBean);
            }
        } else if (device.getDeviceType() == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB) {
            Log.d("DeviceMessagesManager", "Color/brightness adjustable light detected but not supported in this app");
        }
    }
}