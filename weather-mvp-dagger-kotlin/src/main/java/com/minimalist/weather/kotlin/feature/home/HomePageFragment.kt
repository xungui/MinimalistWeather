package com.minimalist.weather.kotlin.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.feature.adapter.DetailAdapter
import com.minimalist.weather.kotlin.feature.adapter.ForecastAdapter
import com.minimalist.weather.kotlin.feature.adapter.LifeIndexAdapter
import com.minimalist.weather.kotlin.feature.adapter.entity.WeatherDetail
import com.minimalist.weather.kotlin.feature.base.BaseFragment
import com.minimalist.weather.kotlin.feature.contract.HomePageContract
import com.minimalist.weather.kotlin.model.data.entity.weather.LifeIndex
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.model.data.entity.weather.WeatherForecast
import com.minimalist.weather.kotlin.widget.IndicatorValueChangeListener
import com.minimalist.weather.kotlin.widget.IndicatorView
import java.util.*
import javax.inject.Inject

@ActivityScoped
class HomePageFragment @Inject constructor() : BaseFragment(), HomePageContract.View {
    //AQI
    @BindView(R.id.tv_aqi)
    lateinit var aqiTextView: TextView
    @BindView(R.id.tv_quality)
    lateinit var qualityTextView: TextView
    @BindView(R.id.indicator_view_aqi)
    lateinit var aqiIndicatorView: IndicatorView
    @BindView(R.id.tv_advice)
    lateinit var adviceTextView: TextView
    @BindView(R.id.tv_city_rank)
    lateinit var cityRankTextView: TextView
    //详细天气信息
    @BindView(R.id.detail_recycler_view)
    lateinit var detailRecyclerView: RecyclerView
    //预报
    @BindView(R.id.forecast_recycler_view)
    lateinit var forecastRecyclerView: RecyclerView
    //生活指数
    @BindView(R.id.life_index_recycler_view)
    lateinit var lifeIndexRecyclerView: RecyclerView

    private var onFragmentInteractionListener: OnFragmentInteractionListener? = null
    private var unbinder: Unbinder? = null
    private var weather: Weather? = null
    private var weatherDetails: MutableList<WeatherDetail>? = null
    private var weatherForecasts: MutableList<WeatherForecast>? = null
    private var lifeIndices: MutableList<LifeIndex>? = null

    private var detailAdapter: DetailAdapter? = null
    private var forecastAdapter: ForecastAdapter? = null
    private var lifeIndexAdapter: LifeIndexAdapter? = null
    @Inject
    lateinit var presenter: HomePageContract.Presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            onFragmentInteractionListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_page, container, false)
        unbinder = ButterKnife.bind(this, rootView)

        //天气详情
        detailRecyclerView.isNestedScrollingEnabled = false
        detailRecyclerView.layoutManager = GridLayoutManager(activity, 3)
        weatherDetails = ArrayList()
        detailAdapter = DetailAdapter(weatherDetails)
        detailAdapter!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> }
        forecastRecyclerView.itemAnimator = DefaultItemAnimator()
        detailRecyclerView.adapter = detailAdapter

        //天气预报
        forecastRecyclerView.isNestedScrollingEnabled = false
        forecastRecyclerView.layoutManager = LinearLayoutManager(activity)
        weatherForecasts = ArrayList()
        forecastAdapter = ForecastAdapter(weatherForecasts)
        forecastAdapter!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> }
        forecastRecyclerView.itemAnimator = DefaultItemAnimator()
        forecastRecyclerView.adapter = forecastAdapter

        //生活指数
        lifeIndexRecyclerView.isNestedScrollingEnabled = false
        lifeIndexRecyclerView.layoutManager = GridLayoutManager(activity, 4)
        lifeIndices = ArrayList()
        lifeIndexAdapter = LifeIndexAdapter(activity!!.applicationContext, lifeIndices)
        lifeIndexAdapter!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Toast.makeText(this@HomePageFragment.context, lifeIndices!![position].details, Toast.LENGTH_LONG).show()
        }
        lifeIndexRecyclerView.itemAnimator = DefaultItemAnimator()
        lifeIndexRecyclerView.adapter = lifeIndexAdapter
        aqiIndicatorView.setIndicatorValueChangeListener(object : IndicatorValueChangeListener {
            override fun onChange(currentIndicatorValue: Int, stateDescription: String, indicatorTextColor: Int) {
                aqiTextView.text = currentIndicatorValue.toString()
                if (TextUtils.isEmpty(weather!!.airQualityLive!!.quality)) {
                    qualityTextView.text = stateDescription
                } else {
                    qualityTextView.text = weather!!.airQualityLive!!.quality
                }
                aqiTextView.setTextColor(indicatorTextColor)
                qualityTextView.setTextColor(indicatorTextColor)
            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        presenter.takeView(this)
    }

    @SuppressLint("SetTextI18n")
    override fun displayWeatherInformation(weather: Weather) {
        this.weather = weather
        onFragmentInteractionListener!!.updatePageTitle(weather)

        val airQualityLive = weather.airQualityLive
        aqiIndicatorView.setIndicatorValue(airQualityLive!!.aqi)
        adviceTextView.text = airQualityLive.advice
        val rank = airQualityLive.cityRank
        cityRankTextView.text = if (TextUtils.isEmpty(rank)) "首要污染物: " + airQualityLive.primary!! else rank

        weatherDetails!!.clear()
        weatherDetails!!.addAll(createDetails(weather))
        detailAdapter!!.notifyDataSetChanged()

        weatherForecasts!!.clear()
        weatherForecasts!!.addAll(weather.weatherForecasts!!)
        forecastAdapter!!.notifyDataSetChanged()

        lifeIndices!!.clear()
        lifeIndices!!.addAll(weather.lifeIndexes!!)
        lifeIndexAdapter!!.notifyDataSetChanged()

        onFragmentInteractionListener!!.addOrUpdateCityListInDrawerMenu(weather)
    }

    private fun createDetails(weather: Weather): List<WeatherDetail> {
        val details = ArrayList<WeatherDetail>()
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "体感温度", weather.weatherLive!!.feelsTemperature!! + "°C"))
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "湿度", weather.weatherLive!!.humidity!! + "%"))
        //        details.add(new WeatherDetail(R.drawable.ic_index_sunscreen, "气压", (int) Double.parseDouble(weather.getWeatherLive().getAirPressure()) + "hPa"));
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "紫外线指数", weather.weatherForecasts!![0].uv))
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "降水量", weather.weatherLive!!.rain!! + "mm"))
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "降水概率", weather.weatherForecasts!![0].pop!! + "%"))
        details.add(WeatherDetail(R.drawable.ic_index_sunscreen, "能见度", weather.weatherForecasts!![0].visibility!! + "km"))
        return details
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
        presenter.dropView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    interface OnFragmentInteractionListener {
        fun updatePageTitle(weather: Weather)

        /**
         * 更新完天气数据同时需要刷新侧边栏的已添加的城市列表
         *
         * @param weather 天气数据
         */
        fun addOrUpdateCityListInDrawerMenu(weather: Weather)
    }
}
