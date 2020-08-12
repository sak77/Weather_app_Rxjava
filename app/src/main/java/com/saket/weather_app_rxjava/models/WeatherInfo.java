package com.saket.weather_app_rxjava.models;

import com.squareup.moshi.Json;

public class WeatherInfo {
    @Json(name = "weather_state_name")
    private String name;
    @Json(name = "weather_state_abbr")
    private String abbr;
    private String min_temp;
    private String max_temp;
    private String wind_speed;
    private String humidity;

    public String getName() {
        return name;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getAbbr() {
        return abbr;
    }
}
