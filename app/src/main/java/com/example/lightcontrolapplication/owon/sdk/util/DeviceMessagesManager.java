package com.example.lightcontrolapplication.owon.sdk.util;

import android.content.Context;

import com.example.lightcontrolapplication.DeviceInfo;
import com.example.lightcontrolapplication.z_UpdateSwitchgearBean;
import com.example.lightcontrolapplication.EPListBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Device message manager class that handles communication with devices and gateways.
 * This class encapsulates methods for controlling and querying devices such as lights, motors, sensors, etc.
 */
public class DeviceMessagesManager {

    private static DeviceMessagesManager instance;
    private List<SocketMessageListener> listeners;
    private boolean isLoggedIn = false;
    private Random random = new Random();

    /**
     * Constructor for DeviceMessagesManager
     */
    public DeviceMessagesManager() {
        listeners = new ArrayList<>();
    }

    /**
     * Singleton pattern implementation to get instance
     * @return DeviceMessagesManager instance
     */
    public static synchronized DeviceMessagesManager getInstance() {
        if (instance == null) {
            instance = new DeviceMessagesManager();
        }
        return instance;
    }

    /**
     * Register a message listener
     * @param listener The listener to register
     */
    public void registerMessageListener(SocketMessageListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregister a message listener
     * @param listener The listener to unregister
     */
    public void unregisterMessageListener(SocketMessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Login to the system using socket connection
     * @param context Android context
     * @param account User account
     * @param password User password
     */
    public void LoginSocket(Context context, String account, String password) {
        // In a real implementation, this would connect to the server and authenticate
        // For this example, we'll simulate a successful login

        // Simulate network delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500); // Simulate 1.5 second network delay

                    // Create login response bean
                    LoginSocketResBean loginRes = new LoginSocketResBean();

                    // Check credentials
                    if ("fbadmin".equals(account) && "fbadmin".equals(password)) {
                        loginRes.setCode(100); // Success
                        isLoggedIn = true;
                    } else {
                        loginRes.setCode(303); // Password error
                    }

                    // Notify listeners
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.LoginSocketRes, loginRes);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Query the list of gateways
     * @param page Page number for pagination
     * @param pagesize Items per page
     */
    public void QueryGatewayList(int page, int pagesize) {
        // In a real implementation, this would send a request to the server
        // For this example, we'll simulate a response
        simulateGatewayListResponse();
    }

    /**
     * Get the list of devices
     */
    public void GetEpList() {
        // In a real implementation, this would send a request to the server
        // For this example, we'll simulate a response
        simulateDeviceListResponse();
    }

    /**
     * Get the state of a device
     * @param device The device info
     * @param cache Use cache flag (0 for no cache, 1 for using cache)
     */
    public void getDeviceState(DeviceInfo device, int cache) {
        // In a real implementation, this would send a request to the server
        // For this example, we'll simulate a response
        simulateDeviceStateResponse(device);
    }

    /**
     * Set up a light switch
     * @param device The device info
     * @param switchOn Whether to turn the switch on or off
     */
    public void setUpSwitch(DeviceInfo device, boolean switchOn) {
        // In a real implementation, this would send a command to the device
        // For this example, we'll simulate a response
        simulateLightSwitchResponse(device, switchOn);
    }

    /**
     * Control a smart curtain (move)
     * @param device The device info
     * @param direction Direction to move (1 for forward, 0 for reverse)
     */
    public void SmartCurtainMove(DeviceInfo device, int direction) {
        // Implementation for curtain movement
        // Not used in the light control app, but included for completeness
    }

    /**
     * Stop a smart curtain
     * @param device The device info
     */
    public void SmartCurtainStop(DeviceInfo device) {
        // Implementation for stopping curtain
        // Not used in the light control app, but included for completeness
    }

    /**
     * Set the brightness of a smart light
     * @param ieee Device IEEE address
     * @param ep Device endpoint
     * @param brightnessValue Brightness value (0-255)
     */
    public void SmartLightSetBrightness(String ieee, int ep, int brightnessValue) {
        // Implementation for setting light brightness
        // Not used in this basic light control app, but would be used in the advanced version
    }

    /**
     * Set the color temperature of a smart light
     * @param ieee Device IEEE address
     * @param ep Device endpoint
     * @param colorTempValue Color temperature value (0-370)
     */
    public void SmartLightSetColorTemp(String ieee, int ep, int colorTempValue) {
        // Implementation for setting light color temperature
        // Not used in this basic light control app, but would be used in the advanced version
    }

    /**
     * Deploy security (arm)
     * @param zoneID Zone ID (default 255)
     */
    public void SecurityDeployment(int zoneID) {
        // Implementation for security deployment
        // Not used in the light control app, but included for completeness
    }

    /**
     * Disarm security
     * @param zoneID Zone ID (default 255)
     */
    public void SecurityDisarming(int zoneID) {
        // Implementation for security disarming
        // Not used in the light control app, but included for completeness
    }

    // ----------------- Simulation methods for testing -----------------

    /**
     * Simulate a gateway list response
     */
    private void simulateGatewayListResponse() {
        // Create a simulated response after a short delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500); // Simulate network delay

                    // Create gateway list data (not used in this app directly)
                    Object gatewayListBean = new Object(); // Placeholder

                    // Notify listeners
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.UpdateEPList, gatewayListBean);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Simulate a device list response
     */
    private void simulateDeviceListResponse() {
        // Create a simulated response after a short delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800); // Simulate network delay

                    // Create a list of simulated devices
                    List<DeviceInfo> devices = new ArrayList<>();

                    // Add different types of devices
                    DeviceInfo light1 = new DeviceInfo();
                    light1.setName("Kitchen Light");
                    light1.setDeviceType(Constants.LIGHT_601);
                    light1.setLinkStatus(true);
                    light1.setIeee("AA:BB:CC:DD:EE:FF:01");
                    light1.setEp(1);
                    devices.add(light1);

                    DeviceInfo light2 = new DeviceInfo();
                    light2.setName("Living Room Light");
                    light2.setDeviceType(Constants.LIGHT_601);
                    light2.setLinkStatus(true);
                    light2.setIeee("AA:BB:CC:DD:EE:FF:02");
                    light2.setEp(1);
                    devices.add(light2);

                    DeviceInfo rgbLight = new DeviceInfo();
                    rgbLight.setName("Bedroom RGB Light");
                    rgbLight.setDeviceType(Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB);
                    rgbLight.setLinkStatus(true);
                    rgbLight.setIeee("AA:BB:CC:DD:EE:FF:03");
                    rgbLight.setEp(1);
                    devices.add(rgbLight);

                    // Add other device types (for completeness)
                    DeviceInfo tempSensor = new DeviceInfo();
                    tempSensor.setName("Temperature Sensor");
                    tempSensor.setDeviceType(DeviceTypeCode.TH_SENSOR);
                    tempSensor.setLinkStatus(true);
                    tempSensor.setIeee("AA:BB:CC:DD:EE:FF:04");
                    tempSensor.setEp(1);
                    devices.add(tempSensor);

                    // Create the device list bean
                    EPListBean deviceListBean = new EPListBean();
                    deviceListBean.setDevices(devices);

                    // Notify listeners
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.ZigBeeGetEPList, deviceListBean);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Simulate a device state response
     * @param device The device to simulate a response for
     */
    private void simulateDeviceStateResponse(DeviceInfo device) {
        // Create a simulated response after a short delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300); // Simulate network delay

                    // For this example, we'll focus on light devices
                    if (device.getDeviceType() == Constants.LIGHT_601 ||
                            device.getDeviceType() == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB) {

                        // Create a light switch status bean
                        z_UpdateSwitchgearBean switchBean = new z_UpdateSwitchgearBean();
                        switchBean.setIeee(device.getIeee());
                        switchBean.setEp(device.getEp());

                        // Randomly determine if the light is on or off
                        boolean isOn = random.nextBoolean();
                        switchBean.setSwitchgear(isOn ? "on" : "off");

                        // Notify listeners
                        for (SocketMessageListener listener : listeners) {
                            listener.getMessage(Constants.UpdateSwitchgear, switchBean);
                        }
                    }
                    // Other device types could be handled here

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Simulate a light switch response
     * @param device The device to simulate a response for
     * @param switchOn Whether the switch is being turned on or off
     */
    private void simulateLightSwitchResponse(DeviceInfo device, boolean switchOn) {
        // Create a simulated response after a short delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500); // Simulate network delay

                    // Create a light switch status bean
                    z_UpdateSwitchgearBean switchBean = new z_UpdateSwitchgearBean();
                    switchBean.setIeee(device.getIeee());
                    switchBean.setEp(device.getEp());
                    switchBean.setSwitchgear(switchOn ? "on" : "off");

                    // Notify listeners - first with SmartLightSetupSwitchgear (command sent confirmation)
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.SmartLightSetupSwitchgear, switchBean);
                    }

                    // Short delay to simulate device response time
                    Thread.sleep(300);

                    // Then with UpdateSwitchgear (actual device state update)
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.UpdateSwitchgear, switchBean);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Simulate a physical light switch operation (e.g., someone pressed the wall switch)
     * @param device The device to simulate a physical interaction for
     * @param switchOn Whether the switch is being turned on or off
     */
    public void simulatePhysicalSwitchOperation(DeviceInfo device, boolean switchOn) {
        // Create a simulated response after a short delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100); // Short delay

                    // Create a light switch status bean
                    z_UpdateSwitchgearBean switchBean = new z_UpdateSwitchgearBean();
                    switchBean.setIeee(device.getIeee());
                    switchBean.setEp(device.getEp());
                    switchBean.setSwitchgear(switchOn ? "on" : "off");

                    // Notify listeners with UpdateLight (physical control event)
                    for (SocketMessageListener listener : listeners) {
                        listener.getMessage(Constants.UpdateLight, switchBean);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}