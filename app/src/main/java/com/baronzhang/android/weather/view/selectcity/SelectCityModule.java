package com.baronzhang.android.weather.view.selectcity;

import dagger.Module;
import dagger.Provides;

import com.baronzhang.android.weather.di.ActivityScoped;
import com.baronzhang.android.weather.view.contract.SelectCityContract;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/11/30
 */
@Module
public class SelectCityModule {
    private SelectCityContract.View view;

    public SelectCityModule(SelectCityContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScoped
    SelectCityContract.View provideSelectCityContractView() {
        return view;
    }
}
