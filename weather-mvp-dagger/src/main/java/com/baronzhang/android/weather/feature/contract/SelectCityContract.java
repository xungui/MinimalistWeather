package com.baronzhang.android.weather.feature.contract;

import java.util.List;

import com.baronzhang.android.weather.model.db.entities.City;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public interface SelectCityContract {

    interface View extends BaseView<Presenter> {

        void displayCities(List<City> cities);
    }

    interface Presenter extends BasePresenter {

        void loadCities();
    }
}
