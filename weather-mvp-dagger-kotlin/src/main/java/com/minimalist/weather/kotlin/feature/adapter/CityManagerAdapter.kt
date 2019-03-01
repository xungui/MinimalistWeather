package com.minimalist.weather.kotlin.feature.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.utils.DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM
import com.minimalist.weather.kotlin.utils.DateConvertUtils.timeStampToDate
import com.minimalist.weather.kotlin.feature.base.BaseRecyclerViewAdapter

/**
 * 城市管理页面Adapter
 *
 */
class CityManagerAdapter(private val weatherList: MutableList<Weather>?) : BaseRecyclerViewAdapter<CityManagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city_manager, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList!![position]
        holder.city.text = weather.cityName
        holder.weather.text = weather.weatherLive?.weather ?: ""
        var forecastString = ""
        if (weather.weatherForecasts != null && weather.weatherForecasts!!.isNotEmpty()) {
            val weatherForecast = weather.weatherForecasts?.get(0)
            forecastString = StringBuilder().append(weatherForecast?.tempMin)
                    .append("~")
                    .append(weatherForecast?.tempMax)
                    .append("℃")
                    .toString()
        }
        holder.temp.text = forecastString
        holder.publishTime.text = "发布于 " + timeStampToDate(weather.weatherLive!!.time, DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
        holder.deleteButton.setOnClickListener { v ->
            val removeWeather = weatherList[holder.adapterPosition]
            weatherList.remove(removeWeather)
            notifyItemRemoved(holder.adapterPosition)
            if (onItemClickListener != null && onItemClickListener is OnCityManagerItemClickListener) {
                (onItemClickListener as OnCityManagerItemClickListener).onDeleteClick(removeWeather.cityId)
            }
        }
    }

    override fun getItemCount(): Int {
        return weatherList?.size ?: 0
    }

    class ViewHolder(itemView: View, adapter: CityManagerAdapter) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.item_delete)
        lateinit var deleteButton: ImageButton
        @BindView(R.id.item_tv_city)
        lateinit var city: TextView
        @BindView(R.id.item_tv_publish_time)
        lateinit var publishTime: TextView
        @BindView(R.id.item_tv_weather)
        lateinit var weather: TextView
        @BindView(R.id.item_tv_temp)
        lateinit var temp: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }
    }

    interface OnCityManagerItemClickListener : AdapterView.OnItemClickListener {

        fun onDeleteClick(cityId: String?)
    }

}
