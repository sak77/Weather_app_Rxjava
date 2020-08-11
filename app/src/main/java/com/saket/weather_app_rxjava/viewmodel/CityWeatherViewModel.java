package com.saket.weather_app_rxjava.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.saket.weather_app_rxjava.models.City;
import com.saket.weather_app_rxjava.repository.CityWeatherRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

/*
ViewModel class survives certain configuration changes which affect activities/fragments.
This is useful to persist data when device rotates and not have to fetch it again...
Used with livedata and databinding to be more effective....
 */
public class CityWeatherViewModel extends ViewModel {
    private static final String TAG = "CityWeatherViewModel";

    Disposable cityInfoDisposable, weatherInfoDisposable;

    List<City> cityList = new ArrayList<>();
    //Can this be private??
    public MutableLiveData<List<City>> mutableLiveCityData = new MutableLiveData<>();

    public MutableLiveData<City> currentSelectedCityLiveData = new MutableLiveData<>();

    private City lastSelectedCity;

    //Should fetch city list from API
    public LiveData<Boolean> shouldFetchAPIData = Transformations.map(currentSelectedCityLiveData, selectedcity -> {
        if (selectedcity == null && lastSelectedCity == null) {
            return true;
        } else {
            lastSelectedCity = selectedcity;
            return false;
        }
    });

    CityWeatherRepo mCityWeatherRepo;
    public CityWeatherViewModel(CityWeatherRepo cityWeatherRepo) {
        mCityWeatherRepo = cityWeatherRepo;
        currentSelectedCityLiveData.setValue(null);
    }

    public void getCityWeatherInfo() {
        String[] arrCityNames = new String[]{"London", "Gothenburg", "Stockholm", "New York", "Berlin"};

        for (String cityName : arrCityNames) {
            cityInfoDisposable = mCityWeatherRepo.getCityInfo(cityName, city -> {
                //City details update callback
                weatherInfoDisposable = mCityWeatherRepo.getCityWeatherInfo(city.getWoeid(), weatherInfo -> {
                    //City weather info update callback
                    city.setCityWeatherInfo(weatherInfo);
                    //Log.v(TAG, "city name " + city.getTitle() + " weather - " + city.getCityWeatherInfo().getName());
                    cityList.add(city);
                    //Note, since we are not using observeOn() to observe values on main thread, we cannot use setValue here.
                    mutableLiveCityData.postValue(cityList);
                });
            });
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (cityInfoDisposable != null) {
            cityInfoDisposable.dispose();
        }
        if (weatherInfoDisposable != null) {
            weatherInfoDisposable.dispose();
        }
    }
}
