package com.baronzhang.android.weather.view.home.drawer;

import com.baronzhang.android.weather.di.scope.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/11/30
 */
@Module
public class DrawerMenuModule {
    private DrawerContract.View view;

    public DrawerMenuModule(DrawerContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScoped
    DrawerContract.View provideCityManagerContactView() {
        return view;
    }
}
