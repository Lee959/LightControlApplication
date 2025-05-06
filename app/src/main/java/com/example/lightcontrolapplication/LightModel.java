package com.example.lightcontrolapplication;

public class LightModel extends DeviceModel{
    private boolean switchgear;
    private String ieee;
    private int ep;

    public LightModel(String name, int deviceType, boolean linkStatus) {
        super(name, deviceType, linkStatus);
        switchgear = false; // set the default value off
    }

    public String getLightStatus () {
        if (switchgear) {
            return "开，ON";
        } else {
            return "关，OFF";
        }
    }
}
