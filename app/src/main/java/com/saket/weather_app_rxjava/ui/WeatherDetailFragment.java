package com.saket.weather_app_rxjava.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saket.weather_app_rxjava.databinding.FragmentWeatherDetailBinding;
import com.saket.weather_app_rxjava.viewmodel.CityViewModelFactory;
import com.saket.weather_app_rxjava.viewmodel.CityWeatherViewModel;

public class WeatherDetailFragment extends Fragment {

    CityWeatherViewModel mCityWeatherViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWeatherDetailBinding binding = FragmentWeatherDetailBinding.inflate(inflater, container, false);
        CityViewModelFactory cityViewModelFactory = new CityViewModelFactory();
        //Passing activity as viewstoreowner since we want to share data between fragments.
        mCityWeatherViewModel = cityViewModelFactory.getInstance(requireActivity());
        binding.setCity(mCityWeatherViewModel.currentSelectedCityLiveData.getValue());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCityWeatherViewModel.currentSelectedCityLiveData.setValue(null);
    }
}
