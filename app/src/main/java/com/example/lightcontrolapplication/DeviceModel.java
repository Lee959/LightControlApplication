package com.example.lightcontrolapplication;


import com.example.lightcontrolapplication.owon.sdk.util.Constants;

public class DeviceModel {
    private String name;
    private String id;
    private String ieee;
    private int ep;
    private int deviceType;
    private boolean linkStatus;

    public DeviceModel(String name, String id, String ieee, int ep, int deviceType, boolean linkStatus) {
        this.name = name;
        this.id = id;
        this.ieee = ieee;
        this.ep = ep;
        this.deviceType = deviceType;
        this.linkStatus = linkStatus;
    }


    // Getter Function
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public String getIeee() { return ieee; }

    public int getEp() { return ep; }

    public boolean isLinkStatus() {
        return linkStatus;
    }


    // Setter Function
    public void setName(String name) {
        this.name = name;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public void setEp(int ep) { this.ep = ep; }

    public void setLinkStatus(boolean linkStatus) {
        this.linkStatus = linkStatus;
    }

    public void setId(String id) { this.id = id; }

    public void setIeee(String ieee) { this.ieee = ieee; }



    public String getDeviceTypeName() {
        switch (deviceType) {
            case Constants.LIGHT_601:
                return "灯，只有开关";
            case Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB:
                return "可调节亮度和色温灯";
            case DeviceTypeCode.TH_SENSOR:
                return "温湿度传感器";
            case DeviceTypeCode.LX_SENSOR:
                return "光照传感器";
            case DeviceTypeCode.SMOKE_SENSOR_ZONE:
                return "烟雾传感器";
            case DeviceTypeCode.MOTION_SENSOR_ZONE:
                return "人体传感器";
            case DeviceTypeCode.AC_SENSOR:
                return "红外转发器";
            case DeviceTypeCode.WARN_SENSOR:
                return "声光报警器";
            case DeviceTypeCode.WARN_MOTOR:
                return "窗帘电机";
            case DeviceTypeCode.DOOR_SENSOR:
                return "门磁传感器";
            default:
                return "未知设备";
        }
    }

    /*

        属性	                                       描述
    Constants.LIGHT_601	                            灯，只有开关
    Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB	    可调节亮度和色温灯
    DeviceTypeCode.TH_SENSOR	                    温湿度传感器
    DeviceTypeCode.LX_SENSOR	                    光照传感器
    DeviceTypeCode.SMOKE_SENSOR_ZONE	            烟雾报警器
    DeviceTypeCode.MOTION_SENSOR_ZONE	            人体传感器
    DeviceTypeCode.AC_SENSOR	                    红外转发器
    DeviceTypeCode.WARN_SENSOR	                    声光报警器
    DeviceTypeCode.WARN_MOTOR	                    窗帘电机
    DeviceTypeCode.DOOR_SENSOR	                    门磁感应器

     */
}