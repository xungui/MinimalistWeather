package com.baronzhang.android.weather.di.component;

import com.baronzhang.android.weather.di.module.ApplicationModule;
import com.baronzhang.android.weather.view.home.drawer.DrawerMenuPresenter;
import com.baronzhang.android.weather.view.selectcity.SelectCityPresenter;

import javax.inject.Singleton;

import dagger.Component;
import com.baronzhang.android.weather.view.home.HomePagePresenter;

/**
 * @author 张磊 (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/12/2
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface PresenterComponent {

    void inject(HomePagePresenter presenter);

    void inject(SelectCityPresenter presenter);

    void inject(DrawerMenuPresenter presenter);
}
 