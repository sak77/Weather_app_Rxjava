package com.saket.weather_app_rxjava.network;

import com.saket.weather_app_rxjava.models.City;
import com.saket.weather_app_rxjava.models.WeatherInfo;
import com.squareup.moshi.Moshi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
API client which provides corresponding retrofit instance to invoke its
respective endpoints.

We use Moshi instead of Gson, as it is built on top of Gson and it supports java and kotlin
 */
public class WeatherAPIClient {

    //Moshi instance
    private static Moshi moshi = new Moshi.Builder()
            .build();
    //Retrofit instance
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.metaweather.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    //Singleton class
    private WeatherAPIClient() {}

    public interface WeatherAPI_Endpoints {
        @GET("location/search/")
        Observable<City[]> getCityInfo(@Query("query") String query);
        //Call<City[]> getCityInfo(@Query("query") String query);

        @GET("location/{woeid}/{date}/")
        Observable<WeatherInfo[]> getCityWeatherInfo(@Path("woeid") String woeid, @Path("date") String date);
        //Call<WeatherInfo[]> getCityWeatherInfo(@Path("woeid") String woeid, @Path("date") String date);
    }

    public static WeatherAPI_Endpoints getWeatherAPIRetrofitInstance() {
        return retrofit.create(WeatherAPI_Endpoints.class);
    }
}
