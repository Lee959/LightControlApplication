package com.example.lightcontrolapplication;

/**
 * Light switch status bean class
 */
public class z_UpdateSwitchgearBean {
    private String switchgear; // "on" or "off"
    private String ieee;
    private int ep;

    public z_UpdateSwitchgearBean() {
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
}