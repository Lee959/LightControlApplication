package com.example.lightcontrolapplication.owon;

import com.example.lightcontrolapplication.DeviceModel;

public class LightModel extends DeviceModel {
    private boolean switchgear;

    public LightModel(String ieee, int ep, String name, int type) {
        super(ieee,ep,name,type);
        this.switchgear = false;
    }

    public boolean isSwitchgear() {
        return switchgear;
    }

    public void setSwitchgear(boolean switchgear) {
        this.switchgear = switchgear;
    }
}

