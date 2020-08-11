package com.saket.weather_app_rxjava.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.saket.weather_app_rxjava.repository.CityWeatherRepo;

/*
ViewModel factory is a wrapper class that injects the required dependency
which is CityWeatherRepo in this case to create and return a new instance of
CityViewModel class.
 */
public class CityViewModelFactory implements ViewModelProvider.Factory {

    //Note that we pass ViewModelStoreOwner here instead of Fragment/Activity
    public CityWeatherViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, this).get(CityWeatherViewModel.class);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(CityWeatherViewModel.class)) {
            CityWeatherRepo cityWeatherRepo = new CityWeatherRepo();
            return (T) new CityWeatherViewModel(cityWeatherRepo);
        } else {
            throw new RuntimeException("Unable to create " + modelClass.getName());
        }
    }
}
