package com.minimalist.weather.kotlin.view.home.drawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.view.adapter.CityManagerAdapter
import com.minimalist.weather.kotlin.view.contract.DrawerContract
import com.minimalist.weather.kotlin.view.main.BaseFragment
import com.minimalist.weather.kotlin.view.selectcity.SelectCityActivity
import java.io.InvalidClassException
import java.util.*
import javax.inject.Inject

@ActivityScoped
class DrawerMenuFragment @Inject constructor() : BaseFragment(), DrawerContract.View {
    @BindView(R.id.add_city_btn)
    lateinit var addCityButton: Button
    @BindView(R.id.rv_city_manager)
    lateinit var cityManagerRecyclerView: RecyclerView
    private lateinit var unbinder: Unbinder
    private var columnCount = 3
    private lateinit var weatherList: MutableList<Weather>
    private lateinit var cityManagerAdapter: CityManagerAdapter
    private var onSelectCity: OnSelectCity? = null
    @Inject
    lateinit var presenter: DrawerContract.Presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSelectCity) {
            onSelectCity = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { columnCount = arguments!!.getInt(ARG_COLUMN_COUNT) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_drawer_menu, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        val context = rootView.context
        if (columnCount <= 1) {
            cityManagerRecyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            cityManagerRecyclerView.layoutManager = GridLayoutManager(context, columnCount)
        }
        cityManagerRecyclerView.itemAnimator = DefaultItemAnimator()
        weatherList = ArrayList()
        cityManagerAdapter = CityManagerAdapter(weatherList)
        cityManagerAdapter.onItemClickListener = itemClickListener
        cityManagerRecyclerView.adapter = cityManagerAdapter
        presenter.subscribe()
        return rootView
    }

    private val itemClickListener = object : CityManagerAdapter.OnCityManagerItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            try {
                presenter.saveCurrentCityToPreference((weatherList as ArrayList<Weather>)[position].cityId)
                onSelectCity?.onSelect(weatherList[position].cityId)
            } catch (e: InvalidClassException) {
                e.printStackTrace()
            }
        }

        override fun onDeleteClick(cityId: String?) {
            presenter.deleteCity(cityId!!)
        }
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
        unbinder.unbind()
        presenter.unSubscribe()
    }

    override fun displaySavedCities(weatherList: List<Weather>) {
        this.weatherList.clear()
        this.weatherList.addAll(weatherList)
        cityManagerAdapter.notifyDataSetChanged()
    }

    @OnClick(R.id.add_city_btn)
    internal fun onAddCityClick() {
        val intent = Intent(activity, SelectCityActivity::class.java)
        startActivity(intent)
    }

    interface OnSelectCity {

        fun onSelect(cityId: String?)
    }

    companion object {
        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): DrawerMenuFragment {
            val fragment = DrawerMenuFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
