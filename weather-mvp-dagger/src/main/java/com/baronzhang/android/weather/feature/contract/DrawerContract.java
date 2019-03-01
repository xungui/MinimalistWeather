package com.baronzhang.android.weather.feature.contract;

import com.baronzhang.android.weather.feature.home.drawer.DrawerMenuPresenter;
import com.baronzhang.android.weather.model.db.entities.minimalist.Weather;

import java.io.InvalidClassException;
import java.util.List;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         16/4/16
 */
public interface DrawerContract {

    interface View extends BaseView<DrawerMenuPresenter> {

        void displaySavedCities(List<Weather> weatherList);
    }

    interface Presenter extends BasePresenter {

        void loadSavedCities();

        void deleteCity(String cityId);

        void saveCurrentCityToPreference(String cityId) throws InvalidClassException;
    }
}
