package com.example.lightcontrolapplication;



public class LightModel extends DeviceModel {
    private boolean switchState;    // true = on, false = off

    // Constructor
    public LightModel(String name, String id, String ieee, int ep, int deviceType, boolean linkStatus) {
        super(name, id, ieee, ep, deviceType, linkStatus);
        this.switchState = false;
    }

    // Set the Light Status
    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    // Return if Light State
    public boolean isSwitchState() {
        return switchState;
    }
}
