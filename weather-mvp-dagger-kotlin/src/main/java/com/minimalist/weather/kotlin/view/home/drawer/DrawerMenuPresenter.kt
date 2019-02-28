package com.minimalist.weather.kotlin.view.home.drawer


import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.model.data.source.WeatherDataRepository
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource
import com.minimalist.weather.kotlin.model.preference.PreferenceHelper
import com.minimalist.weather.kotlin.model.preference.WeatherSettings
import com.minimalist.weather.kotlin.view.contract.DrawerContract
import rx.subscriptions.CompositeSubscription
import java.io.InvalidClassException
import javax.inject.Inject

@ActivityScoped
class DrawerMenuPresenter @Inject constructor() : DrawerContract.Presenter {
    private var view: DrawerContract.View? = null
    private val subscriptions: CompositeSubscription = CompositeSubscription()
    @Inject
    lateinit var weatherRepository: WeatherDataRepository

    override fun takeView(view: DrawerContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun subscribe() {
        loadSavedCities()
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun loadSavedCities() {
        weatherRepository.queryAllSaveCity(object : WeatherDataSource.LoadWeathersCallback {
            override fun onWeatherLoaded(weathers: List<Weather>) {
                view?.displaySavedCities(weathers)
            }

            override fun onDataNotAvailable() {

            }
        })
    }

    override fun deleteCity(cityId: String) {
        var currentCityId = PreferenceHelper.sharedPreferences.getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.id, "")
        weatherRepository.deleteById(cityId)
        if (cityId == currentCityId) {//说明删除的是当前选择的城市，所以需要重新设置默认城市
            weatherRepository.queryAllSaveCity(object : WeatherDataSource.LoadWeathersCallback {
                override fun onWeatherLoaded(weathers: List<Weather>) {
                    if (weathers.isNotEmpty()) {
                        currentCityId = weathers[0].cityId
                        try {
                            PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, currentCityId)
                        } catch (e: InvalidClassException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onDataNotAvailable() {
                }
            })
        }
    }

    @Throws(InvalidClassException::class)
    override fun saveCurrentCityToPreference(cityId: String) {
        PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, cityId)
    }
}
