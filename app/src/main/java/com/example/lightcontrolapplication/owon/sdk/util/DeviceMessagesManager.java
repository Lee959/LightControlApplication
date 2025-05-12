package com.example.lightcontrolapplication.owon.sdk.util;

import com.example.lightcontrolapplication.DeviceModel;
import com.example.lightcontrolapplication.LightModel;
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
    private Random random = new Random();           // For Testing LightApplication

    public DeviceMessagesManager() {
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

    public void QueryGatewayList(int page, int pagesize) {
        simulateGatewayListResponse();
    }

    public void GetEpList() {
        simulateDeviceListResponse();
    }

    /**
     * Get the state of a device
     * @param device The device info
     * @param cache Use cache flag (0 for no cache, 1 for using cache)
     */
    public void getDeviceState(DeviceModel device, int cache) {
        simulateDeviceStateResponse(device);
    }

    /**
     * Set up a light switch
     * @param device The device info
     * @param switchOn Whether to turn the switch on or off
     */
    public void setUpSwitch(DeviceModel device, boolean switchOn) {
        simulateLightSwitchResponse(device, switchOn);
    }

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
                    List<DeviceModel> devices = new ArrayList<>();

                    DeviceModel light1 = new LightModel("AA",1,"Light A",Constants.LIGHT_601);
                    DeviceModel light2 = new DeviceModel("BB",1,"Light B",Constants.LIGHT_601);
                    DeviceModel light3 = new LightModel("CC",2,"Light C",Constants.LIGHT_601);

                    System.out.println(light1.getDeviceType());
                    System.out.println(light2.getDeviceType());

                    devices.add(light1);
                    devices.add(light2);

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
    private void simulateDeviceStateResponse(DeviceModel device) {
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
    private void simulateLightSwitchResponse(DeviceModel device, boolean switchOn) {
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
    public void simulatePhysicalSwitchOperation(DeviceModel device, boolean switchOn) {
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

    public void queryState(String deviceIeee, int deviceEp) {
        // TODO: Incomplete
        return ;
    }

    public void getDeviceList() {
    }
}