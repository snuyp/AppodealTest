package com.appodeal.support.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appodeal.support.test.adapter.ListWeatherAdapter;
import com.appodeal.support.test.mvp.model.Weather;
import com.appodeal.support.test.mvp.presener.WeatherPresenter;
import com.appodeal.support.test.mvp.view.WeatherView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;


public class ListActivity extends MvpAppCompatActivity implements WeatherView {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListWeatherAdapter adapter;
    @InjectPresenter
    WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        recyclerView = findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        weatherPresenter.addSources();
    }

    @Override
    public void onLoadResult(List<Weather> weatherList) {
        adapter = new ListWeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);
    }
}
