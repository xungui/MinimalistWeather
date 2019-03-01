package com.minimalist.weather.kotlin.feature.selectcity


import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.model.data.entity.city.City
import com.minimalist.weather.kotlin.model.preference.PreferenceHelper
import com.minimalist.weather.kotlin.model.preference.WeatherSettings
import com.minimalist.weather.kotlin.feature.adapter.CityListAdapter
import com.minimalist.weather.kotlin.feature.contract.SelectCityContract
import com.minimalist.weather.kotlin.feature.base.BaseFragment
import com.minimalist.weather.kotlin.widget.DividerItemDecoration
import java.io.InvalidClassException
import java.util.*
import javax.inject.Inject

@ActivityScoped
class SelectCityFragment @Inject constructor() : BaseFragment(), SelectCityContract.View {
    lateinit var cities: MutableList<City>
    lateinit var cityListAdapter: CityListAdapter
    @BindView(R.id.rv_city_list)
    lateinit var recyclerView: RecyclerView
    private var unbinder: Unbinder? = null
    @Inject
    lateinit var presenter: SelectCityContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_select_city, container, false)
        unbinder = ButterKnife.bind(this, rootView)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL_LIST))

        cities = ArrayList()
        cityListAdapter = CityListAdapter(cities)
        cityListAdapter.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val selectedCity = cityListAdapter.mFilterData!![position]
                PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, selectedCity.cityId.toString() + "")
                Toast.makeText(activity, selectedCity.cityName, Toast.LENGTH_LONG).show()
                activity!!.finish()
            } catch (e: InvalidClassException) {
                e.printStackTrace()
            }
        }
        recyclerView.adapter = cityListAdapter
        presenter.subscribe()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
        presenter.unSubscribe()
    }

    override fun displayCities(cities: List<City>) {
        this.cities.addAll(cities)
        cityListAdapter.notifyDataSetChanged()
    }
}
