package com.saket.weather_app_rxjava.repository;

import com.saket.weather_app_rxjava.models.City;
import com.saket.weather_app_rxjava.models.WeatherInfo;
import com.saket.weather_app_rxjava.network.WeatherAPIClient;

import java.io.IOException;
import java.util.function.Consumer;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Repository class is responsible for all data operations such as get, post, update delete etc.
It resides between viewmodel and data source such as network API or Room DB.
 */
public class CityWeatherRepo {
    //Below code creates a local dependency on weatherAPI instance....need to optimize this.
    WeatherAPIClient.WeatherAPI_Endpoints weatherAPI_endpoints = WeatherAPIClient.getWeatherAPIRetrofitInstance();

    //We pass a consumer as a parameter which handles callbacks...
    public Disposable getCityInfo(String cityName, Consumer<City> cityConsumer) {
        return weatherAPI_endpoints.getCityInfo(cityName)
                //Using schedulers to ensure operation is performed on background thread.
                //without this, we get network operation on main thread exception.
                .subscribeOn(Schedulers.io())
                .subscribe(cities -> {
                    //Assuming city array has only one item
                    if (cities != null && cities.length > 0) {
                        cityConsumer.accept(cities[0]);
                    }
                });
    }

    public Disposable getCityWeatherInfo(String woeid, Consumer<WeatherInfo> weatherInfoConsumer) {
        return weatherAPI_endpoints.getCityWeatherInfo(woeid, "2020/08/09")
                .subscribe(weatherInfos -> {
                    if (weatherInfos != null && weatherInfos.length > 0) {
                        weatherInfoConsumer.accept(weatherInfos[0]);
                    }
                });
    }
}
