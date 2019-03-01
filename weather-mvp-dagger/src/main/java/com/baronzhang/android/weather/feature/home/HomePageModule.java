package com.baronzhang.android.weather.feature.home;

import com.baronzhang.android.weather.di.ActivityScoped;
import com.baronzhang.android.weather.feature.contract.HomePageContract;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link HomePagePresenter}
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/11/30
 */
@Module
public class HomePageModule {

    private HomePageContract.View view;

    public HomePageModule(HomePageContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScoped
    HomePageContract.View provideHomePageContractView() {
        return view;
    }

}
