package com.example.lightcontrolapplication;

import java.util.LinkedList;
import java.util.List;

public class LightListBean {
    private List<LightModel> light;
    private int totalCount;

    // Constructor
    public LightListBean(List<LightModel> light, int totalCount) {
        this.light = light;
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<LightModel> getLight() {
        return light;
    }

    public void setLight(List<LightModel> light) {
        this.light = light;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "LightListBean{" +
                "light=" + light +
                ", totalCount=" + totalCount +
                '}';
    }
}
