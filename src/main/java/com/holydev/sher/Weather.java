package com.holydev.sher;

public class Weather {
    private String wind;
    private float val;
    private String osd;
    private float osdVal;
    private float temp;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getOsd() {
        return osd;
    }

    public void setOsd(String osd) {
        this.osd = osd;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public Weather() {
    }

    public Weather(String wind, float val, String osd, float osdVal, float temp) {
        this.wind = wind;
        this.val = val;
        this.osd = osd;
        this.osdVal = osdVal;
        this.temp = temp;
    }

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public float getOsdVal() {
        return osdVal;
    }

    public void setOsdVal(float osdVal) {
        this.osdVal = osdVal;
    }
}
