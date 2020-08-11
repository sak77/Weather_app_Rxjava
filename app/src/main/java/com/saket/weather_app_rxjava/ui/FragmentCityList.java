package com.saket.weather_app_rxjava.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.saket.weather_app_rxjava.R;
import com.saket.weather_app_rxjava.databinding.FragmentCitylistBinding;
import com.saket.weather_app_rxjava.viewmodel.CityViewModelFactory;
import com.saket.weather_app_rxjava.viewmodel.CityWeatherViewModel;

import java.util.ArrayList;

public class FragmentCityList extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCitylistBinding binding = FragmentCitylistBinding.inflate(inflater, container, false);
        //binding.setLifecycleOwner(this);
        CityViewModelFactory cityViewModelFactory = new CityViewModelFactory();
        CityWeatherViewModel cityWeatherViewModel = cityViewModelFactory.getInstance(requireActivity());

        CityListAdapter adapter = new CityListAdapter(city -> {
            //City click consumer
            cityWeatherViewModel.currentSelectedCityLiveData.setValue(city);
        });
        binding.citylist.setAdapter(adapter);

        cityWeatherViewModel.mutableLiveCityData.observe(getViewLifecycleOwner(), cities -> {
            adapter.submitList(new ArrayList<>(cities));
        });


        //Load data only first time screen is loaded
        cityWeatherViewModel.shouldFetchAPIData.observe(getViewLifecycleOwner(), shouldFetchData -> {
            if (shouldFetchData) {
                if (savedInstanceState == null) {
                    cityWeatherViewModel.getCityWeatherInfo();
                }
            }
        });

        //Handle click and navigation
        cityWeatherViewModel.currentSelectedCityLiveData.observe(getViewLifecycleOwner(), city -> {
            if (city != null) {
                //Navigate to weather detail fragment
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeatherDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}
