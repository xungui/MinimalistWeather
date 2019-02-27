package com.minimalist.weather.kotlin.view.selectcity

import android.content.Context
import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.ApplicationModule
import com.minimalist.weather.kotlin.di.DaggerPresenterComponent
import com.minimalist.weather.kotlin.main.WeatherApplication
import com.minimalist.weather.kotlin.model.data.Injection
import com.minimalist.weather.kotlin.model.data.entity.city.City
import com.minimalist.weather.kotlin.model.data.source.CityDataRepository
import com.minimalist.weather.kotlin.model.data.source.CityDataSource
import com.minimalist.weather.kotlin.model.data.source.local.CityDao
import com.minimalist.weather.kotlin.view.contract.SelectCityContract
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

@ActivityScoped
class SelectCityPresenter @Inject
internal constructor(context: Context, private val cityListView: SelectCityContract.View) : SelectCityContract.Presenter {
    private val subscriptions: CompositeSubscription


    init {
        this.subscriptions = CompositeSubscription()
        cityListView.setPresenter(this)
        DaggerPresenterComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build().inject(this)
    }

    override fun loadCities() {
        Injection.provideCityRepository(WeatherApplication.instance).getCities(object : CityDataSource.LoadCitiesCallback {
            override fun onCitiesLoaded(cities: List<City>) {
                cityListView.displayCities(cities)
            }

            override fun onDataNotAvailable() {

            }
        })
    }

    override fun subscribe() {
        loadCities()
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }
}
