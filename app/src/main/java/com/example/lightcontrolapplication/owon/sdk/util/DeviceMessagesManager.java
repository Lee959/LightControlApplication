package com.example.lightcontrolapplication.owon.sdk.util;

import com.example.lightcontrolapplication.DeviceListBean;
import com.example.lightcontrolapplication.DeviceModel;
import com.example.lightcontrolapplication.DeviceTypeCode;
import com.example.lightcontrolapplication.LightModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Device message manager class that handles communication with devices and gateways.
 * This class encapsulates methods for controlling and querying devices such as lights, motors, sensors, etc.
 */
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

//    public void QueryGatewayList(int page, int pagesize) {
//        // TODO: QueryGatewayList : Send a request to the server
//        simulateGatewayListResponse();
//    }

    public void GetEpList() {
        // TODO: GetEpList: Send a request to the server
        simulateDeviceListResponse();
    }

    /**
     * Get the state of a device.
     *
     * @param device The device to query.
     * @param cache  Use cache flag (0 for no cache, 1 for using cache).
     */
    public void getDeviceState(DeviceModel device, int cache) {
        // TODO: getDeviceState: Send a request to the server
        simulateDeviceStateResponse(device, cache);
    }

    /**
     * Testing: Simulate a device list response for demonstration purposes.
     */
    private void simulateDeviceListResponse() {
        // Create a list of simulated devices
        List<DeviceModel> devices = new ArrayList<>();

        // Add different types of devices
        devices.add(new DeviceModel("Light A", "dev001", "AA", 1, Constants.LIGHT_601, false));
        devices.add(new DeviceModel("Light B", "dev002", "BB", 1, Constants.LIGHT_601, false));
        devices.add(new DeviceModel("Light C", "dev003", "CC", 1, Constants.LIGHT_601, false));
        devices.add(new DeviceModel("Light D", "dev004", "DD", 1, Constants.LIGHT_601, false));
        devices.add(new DeviceModel("Light E", "dev005", "EE", 1, Constants.LIGHT_601, false));


        DeviceListBean deviceListBean = new DeviceListBean(devices);

        for (SocketMessageListener listener : listeners) {
            listener.getMessage(Constants.ZigBeeGetEPList, deviceListBean);
        }
    }

    /**
     * Testing: Simulate a device state response for demonstration purposes.
     *
     * @param device The device to simulate a response for.
     */
    private void simulateDeviceStateResponse(DeviceModel device, int cache) {
        // For demonstration purposes, we'll simulate different responses based on device type
        int commandID;
        Object bean;

        switch (device.getDeviceType()) {
            case Constants.LIGHT_601:
                commandID = Constants.LIGHT_601;
                bean = createLightStateBean(device);
            case Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB:
                commandID = Constants.UpdateSwitchgear;
                bean = createLightStateBean(device);
                break;

            case DeviceTypeCode.TH_SENSOR:
                commandID = Constants.THI_UPDATE;
                bean = createTempHumidityStateBean(device);
                break;

            case DeviceTypeCode.LX_SENSOR:
                commandID = Constants.ILLUM_UPDATE;
                bean = createLightSensorStateBean(device);
                break;

            case DeviceTypeCode.MOTION_SENSOR_ZONE:
                commandID = Constants.MotionSensorUpdate;
                bean = createMotionSensorStateBean(device);
                break;

            case DeviceTypeCode.SMOKE_SENSOR_ZONE:
                commandID = Constants.WarningSensor;
                bean = createSmokeDetectorStateBean(device);
                break;

            default:
                commandID = 0;
                bean = null;
                break;
        }

        if (bean != null) {
            for (SocketMessageListener listener : listeners) {
                listener.getMessage(commandID, bean);
            }
        }
    }

    // Helper Function: Create device state beans for demonstration purposes

    private Object createLightStateBean(DeviceModel device) { return new Object(); }

    private Object createTempHumidityStateBean(DeviceModel device) {
        return new Object();
    }

    private Object createLightSensorStateBean(DeviceModel device) {
        return new Object();
    }

    private Object createMotionSensorStateBean(DeviceModel device) {
        return new Object();
    }

    private Object createSmokeDetectorStateBean(DeviceModel device) {
        return new Object();
    }
}