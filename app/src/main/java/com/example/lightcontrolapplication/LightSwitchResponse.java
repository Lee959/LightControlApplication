package com.example.lightcontrolapplication;

public class LightSwitchResponse {
    private String switchgear;
    private String ieee;
    private int ep;

    public LightSwitchResponse(String switchgear, String ieee, int ep) {
        this.switchgear = switchgear;
        this.ieee = ieee;
        this.ep = ep;
    }

    public String getSwitchgear() {
        return switchgear;
    }

    public void setSwitchgear(String switchgear) {
        this.switchgear = switchgear;
    }

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

    public boolean isOn() {
        return "on".equals(switchgear);
    }
}