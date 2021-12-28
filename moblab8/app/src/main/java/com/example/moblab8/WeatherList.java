package com.example.moblab8;

import java.math.BigDecimal;

public class WeatherList {
    String ct,desc,day;
    Float temperature;
    public String getCt() {
        return ct;
    }
    public String getDay() {
        return day;
    }
    public String getDesc() {
        return desc;
    }
    public Float getTmp() {
        return temperature;
    }
    public void setCt(String ct) {
        this.ct = ct;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setTmp(Float tmp) {
        this.temperature = BigDecimal.valueOf(tmp).setScale(2, BigDecimal.ROUND_HALF_DOWN).floatValue();
    }
}
