package com.minimalist.weather.kotlin.view.home


import android.content.Context
import android.widget.Toast
import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.ApplicationModule
import com.minimalist.weather.kotlin.di.DaggerPresenterComponent
import com.minimalist.weather.kotlin.model.data.Injection
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.model.data.source.WeatherDataRepository
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource
import com.minimalist.weather.kotlin.model.data.source.local.WeatherDao
import com.minimalist.weather.kotlin.model.preference.PreferenceHelper
import com.minimalist.weather.kotlin.model.preference.WeatherSettings
import com.minimalist.weather.kotlin.utils.NetworkUtils
import com.minimalist.weather.kotlin.utils.RxSchedulerUtils
import com.minimalist.weather.kotlin.view.contract.HomePageContract
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

@ActivityScoped
class HomePagePresenter @Inject
internal constructor(private val context: Context, private val weatherView: HomePageContract.View) : HomePageContract.Presenter {
    private val subscriptions: CompositeSubscription = CompositeSubscription()


    init {
        weatherView.setPresenter(this)
        DaggerPresenterComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build().inject(this)
    }

    override fun subscribe() {
        val cityId = PreferenceHelper.sharedPreferences.getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.id, "")
        loadWeather(cityId, false)
    }

    override fun loadWeather(cityId: String, refreshNow: Boolean) {
        if (!NetworkUtils.isNetworkConnected(context)) {
            Toast.makeText(context, "网络不可用，请检查您的网络设置", Toast.LENGTH_LONG).show()
        }
        Injection.provideWeatherRepository(context).getWeather(cityId, object : WeatherDataSource.LoadWeatherCallback {
            override fun onWeatherLoaded(weather: Weather) {
                weatherView.displayWeatherInformation(weather)
            }

            override fun onDataNotAvailable() {
                Toast.makeText(context, "无法获取天气信息", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }
}
