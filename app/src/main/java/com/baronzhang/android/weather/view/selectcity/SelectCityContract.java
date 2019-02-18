package com.baronzhang.android.weather.view.selectcity;

import java.util.List;

import com.baronzhang.android.weather.model.db.entities.City;
import com.baronzhang.android.weather.view.main.BasePresenter;
import com.baronzhang.android.weather.view.main.BaseView;

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
