package com.example.weatherly;

public class WeatherModel {
    String temperature;
    String icon;
    String time;
    String windspeed;

    public WeatherModel(String temperature, String icon, String time, String windspeed) {
        this.temperature = temperature;
        this.icon = icon;
        this.time = time;
        this.windspeed = windspeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }
}
