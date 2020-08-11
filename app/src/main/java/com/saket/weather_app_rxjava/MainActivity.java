package com.saket.weather_app_rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.saket.weather_app_rxjava.databinding.ActivityMainBinding;
import com.saket.weather_app_rxjava.ui.FragmentCityList;

/**
 * Date started - 07 August 2020.
 * Date completed -
 * Purpose of this app is to have a clean implementation of the Weather app with Rxjava.
 * Schedulers can be used for multi-threaded calls and returning stream of data to the viewmodel.
 * I can also use some merge operations to return a combined result.
 * Functional programming - Yes
 * Rxjava2 - Yes
 * Retrofit - Yes
 * Viewbinding - Yes
 * Databinding - Yes
 * Binding adapter - Yes
 * Navigation graph - No
 * MVVM pattern - Yes
 * LiveData & ViewModel- Yes
 * Repository - Yes
 * Room DB - No
 * ListAdapter for Recyclerview - Yes
 * WorkManager API - No
 * Guidelines in layout - Yes
 * <p>
 * MainActivity Learnings -
 */
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    ActivityMainBinding binding;

    /*
    Step 1: add gradle dependencies
        Enable databinding and viewbinding
        add rxjava dependency
        add java 8 dependency for app module..
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Important to call setSupportActionBar before setting nav icon click listener
        setSupportActionBar(binding.toolbar);
        //Toolbar setup
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
        });
        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_add);
        //directly setting title on binding.toolbar does not work for some reason...
        getSupportActionBar().setTitle("Home screen");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //checking savedInstanceState is null prevents fragment from getting added when screen is rotated.
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, new FragmentCityList());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackStackChanged() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        binding.toolbar.setNavigationIcon(count == 0 ? android.R.drawable.ic_menu_add : android.R.drawable.ic_menu_upload);
    }
}