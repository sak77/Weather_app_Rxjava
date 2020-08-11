package com.saket.weather_app_rxjava.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.weather_app_rxjava.databinding.CityListItemBinding;
import com.saket.weather_app_rxjava.models.City;

import java.util.function.Consumer;

/*
    Viewholder holds actual data. The adapter is only a wrapper over it and does not hold any data.
 */
public class CityListAdapter extends ListAdapter<City, CityListAdapter.CityInfoViewHolder> {

    Consumer<City> mCityClickConsumer;
    protected CityListAdapter(Consumer<City> cityClickConsumer) {

        super(MyDiffUtilCallback);
        mCityClickConsumer = cityClickConsumer;
    }

    //DiffUtils.itemcallback - this has to be private static final to be accepted to the constructor
    private static final DiffUtil.ItemCallback<City> MyDiffUtilCallback = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            //Match unique ids
            return oldItem.getWoeid().equals(newItem.getWoeid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public CityInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityInfoViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CityInfoViewHolder holder, int position) {
        City currCity = getItem(position);
        holder.bind(currCity, mCityClickConsumer);
    }

    static class CityInfoViewHolder extends RecyclerView.ViewHolder {
        CityListItemBinding binding;

        public CityInfoViewHolder(@NonNull CityListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static CityInfoViewHolder from(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            CityListItemBinding binding = CityListItemBinding.inflate(layoutInflater, parent, false);
            return new CityInfoViewHolder(binding);
        }

        public void bind(City city, Consumer<City> cityClickConsumer) {
            binding.setCity(city);
            binding.getRoot().setOnClickListener(v -> {
                cityClickConsumer.accept(city);
            });
            binding.executePendingBindings();
        }
    }
}
