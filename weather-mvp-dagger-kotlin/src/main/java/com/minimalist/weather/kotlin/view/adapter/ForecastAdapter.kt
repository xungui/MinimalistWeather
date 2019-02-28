package com.minimalist.weather.kotlin.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.model.data.entity.weather.WeatherForecast
import com.minimalist.weather.kotlin.view.main.BaseRecyclerViewAdapter

class ForecastAdapter(private val weatherForecasts: List<WeatherForecast>?) : BaseRecyclerViewAdapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(itemView, this)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val weatherForecast = weatherForecasts!![position]
        holder.weekTextView.text = weatherForecast.week
        holder.dateTextView.text = weatherForecast.date
        holder.weatherIconImageView.setImageResource(R.mipmap.ic_launcher)
        holder.weatherTextView.text = if (TextUtils.isEmpty(weatherForecast.weather))
            if (weatherForecast.weatherDay == weatherForecast.weatherNight)
                weatherForecast.weatherDay
            else
                weatherForecast.weatherDay + "转" + weatherForecast.weatherNight
        else
            weatherForecast.weather
        holder.tempMaxTextView.text = weatherForecast.tempMax.toString() + "°"
        holder.tempMinTextView.text = weatherForecast.tempMin.toString() + "°"
    }

    override fun getItemCount(): Int {
        return weatherForecasts?.size ?: 0
    }

      class ViewHolder(itemView: View, adapter: ForecastAdapter) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.week_text_view)
        lateinit var weekTextView: TextView
        @BindView(R.id.date_text_view)
        lateinit var dateTextView: TextView
        @BindView(R.id.weather_icon_image_view)
        lateinit var weatherIconImageView: ImageView
        @BindView(R.id.weather_text_view)
        lateinit var weatherTextView: TextView
        @BindView(R.id.temp_max_text_view)
        lateinit var tempMaxTextView: TextView
        @BindView(R.id.temp_min_text_view)
        lateinit var tempMinTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }
    }
}
