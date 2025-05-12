package com.example.lightcontrolapplication;

import com.example.lightcontrolapplication.owon.sdk.util.Constants;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    // Basic device identifiers
    private String ieee;            // Device physical address
    private int ep;                 // Device endpoint
    private String name;            // Device name
    private int type;               // Device type code (DeviceTypeCode.java)
    private boolean online;         // Online status
    private boolean switchState;    // true = on, false = off


    public DeviceInfo() {
        this.ieee = "";
        this.ep = 0;
        this.name = "Unknown Device";
        this.type = 0;
        this.online = false;
        this.switchState = false;
    }

    /**
     * Constructor with basic parameters
     */
    public DeviceInfo(String ieee, int ep, String name, int type) {
        this();  // Call default constructor to initialize all fields
        this.ieee = ieee;
        this.ep = ep;
        this.name = name;
        this.type = type;
    }

    /**
     * Convert from DeviceInfoBean
     */
    public static DeviceInfo fromDeviceInfoBean(DeviceInfoBean bean) {
        DeviceInfo info = new DeviceInfo();
        if (bean != null) {
            info.setIeee(bean.getIeee());
            info.setEp(bean.getEp());
            info.setName(bean.getName());
            info.setType(bean.getType());
            info.setOnline(bean.isOnline());
        }
        return info;
    }

    /**
     * Convert to DeviceInfoBean
     */
    public DeviceInfoBean toDeviceInfoBean() {
        DeviceInfoBean bean = new DeviceInfoBean();
        bean.setIeee(this.ieee);
        bean.setEp(this.ep);
        bean.setName(this.name);
        bean.setType(this.type);
        bean.setOnline(this.online);
        return bean;
    }

    /**
     * Update switch state from z_UpdateSwitchgearBean
     */
    public void updateFromSwitchgearBean(z_UpdateSwitchgearBean bean) {
        if (bean != null) {
            if ("on".equals(bean.getSwitchgear())) {
                this.switchState = true;
            } else if ("off".equals(bean.getSwitchgear())) {
                this.switchState = false;
            }
        }
    }

    /**
     * Check if this device is a light
     */
    public boolean isLightDevice() {
        return this.type == Constants.LIGHT_601 ||
                this.type == Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB;
    }


    // Getters and Setters

    public String getIeee() {
        return ieee;
    }

    public void setIeee(String ieee) {
        this.ieee = ieee;
    }

    public int getEp() {
        return ep;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean isSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColorTemperature() {
        return colorTemperature;
    }

    public void setColorTemperature(int colorTemperature) {
        this.colorTemperature = colorTemperature;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(float illuminance) {
        this.illuminance = illuminance;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public void setMotionDetected(boolean motionDetected) {
        this.motionDetected = motionDetected;
    }

    public boolean isSmokeDetected() {
        return smokeDetected;
    }

    public void setSmokeDetected(boolean smokeDetected) {
        this.smokeDetected = smokeDetected;
    }
}
