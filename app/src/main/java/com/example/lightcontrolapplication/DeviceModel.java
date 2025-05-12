package com.example.lightcontrolapplication;

import java.io.Serializable;

public class DeviceModel implements Serializable {

    // Basic device identifiers
    private String ieee;            // Device physical address
    private int ep;                 // Device endpoint
    private String name;            // Device name
    private int type;               // Device type code (DeviceTypeCode.java)
    private boolean online;         // Online status


    public DeviceModel() {
        this.ieee = "";
        this.ep = 0;
        this.name = "Unknown Device";
        this.type = DeviceTypeCode.UNKNOW_DEVICE;
        this.online = false;
    }

    /**
     * Constructor with basic parameters
     */
    public DeviceModel(String ieee, int ep, String name, int type) {
        this();  // Call default constructor to initialize all fields
        this.ieee = ieee;
        this.ep = ep;
        this.name = name;
        this.type = type;
    }

    /**
     * Convert from DeviceInfoBean
     */
    public static DeviceModel fromDeviceInfoBean(DeviceInfoBean bean) {
        DeviceModel info = new DeviceModel();
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

    public int getDeviceType() { return this.type; }

    public void setDeviceType(int deviceType) {this.type = deviceType; }


}
