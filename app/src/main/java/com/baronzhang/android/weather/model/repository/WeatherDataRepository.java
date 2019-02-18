package com.baronzhang.android.weather.model.repository;

import android.content.Context;
import android.text.TextUtils;

import com.baronzhang.android.weather.model.db.dao.WeatherDao;
import com.baronzhang.android.weather.model.db.entities.adapter.CloudWeatherAdapter;
import com.baronzhang.android.weather.model.db.entities.minimalist.Weather;
import com.baronzhang.android.weather.model.http.ApiClient;
import com.baronzhang.android.weather.model.http.service.EnvironmentCloudWeatherService;
import com.baronzhang.android.weather.utils.NetworkUtils;

import java.sql.SQLException;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 * 2016/12/10
 */
public class WeatherDataRepository {

    public static Observable<Weather> getWeather(Context context, String cityId, WeatherDao weatherDao, boolean refreshNow) {

        //从数据库获取天气数据
        Observable<Weather> observableForGetWeatherFromDB = Observable.create(subscriber -> {
            try {
                Weather weather = weatherDao.queryWeather(cityId);
                subscriber.onNext(weather);
                subscriber.onCompleted();
            } catch (SQLException e) {
                throw Exceptions.propagate(e);
            }
        });

        if (!NetworkUtils.isNetworkConnected(context))
            return observableForGetWeatherFromDB;

        //从服务端获取天气数据
        EnvironmentCloudWeatherService service = ApiClient.getService();
        Observable<Weather> observableForGetWeatherFromNetWork = Observable.combineLatest(
                service.getWeatherLive(cityId),
                service.getWeatherForecast(cityId),
                service.getAirLive(cityId),
                (weatherLive, forecast, airLive) -> new CloudWeatherAdapter(weatherLive, forecast, airLive).getWeather());
        assert observableForGetWeatherFromNetWork != null;
        observableForGetWeatherFromNetWork = observableForGetWeatherFromNetWork.doOnNext(weather -> Schedulers.io().createWorker().schedule(() -> {
            try {
                weatherDao.insertOrUpdateWeather(weather);
            } catch (SQLException e) {
                throw Exceptions.propagate(e);
            }
        }));

        return Observable.concat(observableForGetWeatherFromDB, observableForGetWeatherFromNetWork)
                .filter(weather -> weather != null && !TextUtils.isEmpty(weather.getCityId()))
                .distinct(weather -> weather.getWeatherLive().getTime())
                .takeUntil(weather -> !refreshNow && System.currentTimeMillis() - weather.getWeatherLive().getTime() <= 15 * 60 * 1000);
    }
}
