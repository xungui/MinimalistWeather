package com.minimalist.weather.kotlin.view.selectcity

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.model.data.entity.city.City
import com.minimalist.weather.kotlin.model.data.source.CityDataRepository
import com.minimalist.weather.kotlin.model.data.source.CityDataSource
import com.minimalist.weather.kotlin.view.contract.SelectCityContract
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

@ActivityScoped
class SelectCityPresenter @Inject
internal constructor() : SelectCityContract.Presenter {
    private val subscriptions: CompositeSubscription = CompositeSubscription()
    private var cityListView: SelectCityContract.View? = null
    @Inject
    lateinit var cityDataRepository: CityDataRepository

    override fun takeView(view: SelectCityContract.View) {
        cityListView = view
    }

    override fun dropView() {
        cityListView = null
    }

    override fun loadCities() {
        cityDataRepository.getCities(object : CityDataSource.LoadCitiesCallback {
            override fun onCitiesLoaded(cities: List<City>) {
                cityListView?.displayCities(cities)
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
