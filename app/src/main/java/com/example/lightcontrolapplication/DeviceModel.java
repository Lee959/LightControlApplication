package com.example.lightcontrolapplication;

public class DeviceModel {
    private String name;
    private int deviceType;
    private boolean linkStatus;

    // Constructor
    public DeviceModel (String name, int deviceType, boolean linkStatus) {
        this.name = name;
        this.deviceType = deviceType;
        this.linkStatus = linkStatus;
    }

    // getter and setter


    public String getName() {
        return name;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public boolean isLinkStatus() {
        return linkStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public void setLinkStatus(boolean linkStatus) {
        this.linkStatus = linkStatus;
    }

}
