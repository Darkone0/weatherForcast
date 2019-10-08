package com.example.air.wf.model;

public class WeatherModel {
    private String date;
    private int temp;
    private int lowest_temp;
    private int highest_temp;
    private String range;
    private String weather;
    private String wid;
    private String curWeather;
    private int isDayT;

    public int getIsDayT() {
        return isDayT;
    }

    public void setIsDayT(int isDayT) {
        this.isDayT = isDayT;
    }

    public String getCurWeather() {
        return curWeather;
    }

    public void setCurWeather(String curWeather) {
        this.curWeather = curWeather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getLowest_temp() {
        return lowest_temp;
    }

    public void setLowest_temp(int lowest_temp) {
        this.lowest_temp = lowest_temp;
    }

    public int getHighest_temp() {
        return highest_temp;
    }

    public void setHighest_temp(int highest_temp) {
        this.highest_temp = highest_temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
