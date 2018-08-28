package com.appodeal.support.test.mvp.presener;

import com.appodeal.support.test.R;
import com.appodeal.support.test.mvp.model.Weather;
import com.appodeal.support.test.mvp.view.WeatherView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {
    List<Weather> sourcesList = new ArrayList<>();

    public void addSources() {
        if (sourcesList.size() == 0) {
            sourcesList.add(new Weather(1, "Example", R.drawable.ic_ac_unit_black_24dp));
            sourcesList.add(new Weather(2, "Brightness_3", R.drawable.ic_brightness_3_black_24dp));
            sourcesList.add(new Weather(3, "Cloudy", R.drawable.ic_wb_cloudy_black_24dp));
            sourcesList.add(new Weather(4, "Sunny", R.drawable.ic_wb_sunny_black_24dp));
            sourcesList.add(new Weather(4, "Brightness_1", R.drawable.ic_brightness_1_black_24dp));
        }
        getViewState().onLoadResult(sourcesList);
    }
}
