package com.saket.weather_app_rxjava;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.saket.weather_app_rxjava.models.WeatherInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by sshriwas on 2020-08-12
 */
public class BindingUtils {

    //First parameter is view on which binding is applied,
    //Second param is data which has to be bound.
    //NOTE: Make sure the method is static otherwise the binding will fail. This
    //is because otherwise Binding class requires an instance of BindingUtils class to get this method
    //which does not exist.
    //To see usage go to fragment_weather_detail.xml. There you will find for imageview -
    //app:weatherIcon="@{city.cityWeatherInfo}"
    @BindingAdapter(value = "weatherIcon")
    public static void setWeatherIcon(ImageView imgIcon, WeatherInfo weatherInfo) {
        String abbr = weatherInfo.getAbbr();
        String url = "https://www.metaweather.com/static/img/weather/png/64/" + abbr + ".png";
        Picasso.get().load(url).into(imgIcon);
    }
}
