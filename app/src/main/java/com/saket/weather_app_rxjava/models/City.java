package com.saket.weather_app_rxjava.models;

import java.util.Objects;

public class City {
    private String title;
    private String woeid;
    private WeatherInfo cityWeatherInfo;

    public City(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public String getWoeid() {
        return woeid;
    }

    public WeatherInfo getCityWeatherInfo() {
        return cityWeatherInfo;
    }

    public void setCityWeatherInfo(WeatherInfo cityWeatherInfo) {
        this.cityWeatherInfo = cityWeatherInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return title.equals(city.title) &&
                woeid.equals(city.woeid) &&
                Objects.equals(cityWeatherInfo, city.cityWeatherInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, woeid, cityWeatherInfo);
    }
}
