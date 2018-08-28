package com.appodeal.support.test.mvp.view;

import com.appodeal.support.test.mvp.model.Weather;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
    void onLoadResult(List<Weather> weatherList);
}
