package com.minimalist.weather.kotlin.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.annimon.stream.Stream
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.model.data.entity.city.City
import com.minimalist.weather.kotlin.view.main.BaseRecyclerViewAdapter
import java.util.*

class CityListAdapter(private val cities: List<City>) : BaseRecyclerViewAdapter<CityListAdapter.ViewHolder>(), Filterable {
    var mFilterData: List<City>? = null//过滤后的数据
    private var filter: RecyclerViewFilter =  RecyclerViewFilter()

    init {
        mFilterData = cities
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = mFilterData!![position]
        var cityName = city.cityName
        val parentName = city.parent
        if (cityName != parentName) {
            cityName = "$parentName.$cityName"
        }
        holder.cityNameTextView!!.text = cityName
    }

    override fun getItemCount(): Int {
        return if (mFilterData == null) 0 else mFilterData!!.size
    }

     class ViewHolder(itemView: View, cityListAdapter: CityListAdapter) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.city_name_text_view)
        lateinit var cityNameTextView: TextView

         init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { cityListAdapter.onItemHolderClick(this) }
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private inner class RecyclerViewFilter : Filter() {

        override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
            //返回的results即为过滤后的ArrayList<City>
            val results = Filter.FilterResults()
            //无约束字符串则返回null
            if (charSequence == null || charSequence.length == 0) {
                results.values = null
                results.count = 0
            } else {
                val prefixString = charSequence.toString().toLowerCase()
                //新建Values存放过滤后的数据
                val newValues = ArrayList<City>()
                Stream.of(cities)
                        .filter { city ->
                            (city.cityName!!.contains(prefixString)
                                    || city.cityNameEn!!.contains(prefixString)
                                    || city.parent!!.contains(prefixString)
                                    || city.root!!.contains(prefixString))
                        }
                        .forEach { newValues.add(it) }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
            if (filterResults.count > 0) {
                mFilterData = filterResults.values as List<City>?
                notifyDataSetChanged()//重绘当前可见区域
            } else {
                mFilterData = cities
                notifyDataSetChanged()//会重绘控件（还原到初始状态）
            }
        }
    }
}
