package com.minimalist.weather.kotlin.view.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.main.WeatherApplication
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.utils.ActivityUtils
import com.minimalist.weather.kotlin.utils.DateConvertUtils
import com.minimalist.weather.kotlin.view.home.drawer.DrawerMenuFragment
import com.minimalist.weather.kotlin.view.home.drawer.DrawerMenuPresenter
import com.minimalist.weather.kotlin.view.main.BaseActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import javax.inject.Inject

class MainActivity : BaseActivity(), HomePageFragment.OnFragmentInteractionListener, DrawerMenuFragment.OnSelectCity {
    @BindView(R.id.refresh_layout)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    @BindView(R.id.collapsing_toolbar)
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout

    //基本天气信息
    @BindView(R.id.temp_text_view)
    lateinit var tempTextView: TextView
    @BindView(R.id.weather_text_view)
    lateinit var weatherNameTextView: TextView
    @BindView(R.id.publish_time_text_view)
    lateinit var realTimeTextView: TextView

    @Inject
    lateinit var homePagePresenter: HomePagePresenter
    private lateinit var drawerMenuPresenter: DrawerMenuPresenter
    private var currentCityId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        //设置 Header 为 Material风格
        val header = ClassicsHeader(this)
        header.setPrimaryColors(this.resources.getColor(R.color.colorPrimary), Color.WHITE)
        smartRefreshLayout.refreshHeader = header
        smartRefreshLayout.setOnRefreshListener {
            currentCityId?.let { it -> homePagePresenter.loadWeather(it, true) }
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        var homePageFragment: HomePageFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container) as? HomePageFragment
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, homePageFragment, R.id.fragment_container)
        }
        DaggerHomePageComponent.builder()
                .applicationComponent(WeatherApplication.instance!!.applicationComponent)
                .homePageModule(HomePageModule(homePageFragment))
                .build().inject(this)

        var drawerMenuFragment: DrawerMenuFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container_drawer_menu) as? DrawerMenuFragment
        if (drawerMenuFragment == null) {
            drawerMenuFragment = DrawerMenuFragment.newInstance(1)
            ActivityUtils.addFragmentToActivity(supportFragmentManager, drawerMenuFragment, R.id.fragment_container_drawer_menu)
        }
        drawerMenuPresenter = DrawerMenuPresenter(this, drawerMenuFragment)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)!!
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_about) {
            return true
        } else if (id == R.id.action_feedback) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updatePageTitle(weather: Weather) {
        currentCityId = weather.cityId
        smartRefreshLayout.finishRefresh()
        toolbar.title = weather.cityName
        collapsingToolbarLayout.title = weather.cityName

        tempTextView.text = weather.weatherLive!!.temp
        weatherNameTextView.text = weather.weatherLive!!.weather
        realTimeTextView.text = getString(R.string.string_publish_time) +
                DateConvertUtils.timeStampToDate(weather.weatherLive!!.time, DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
    }

    override fun addOrUpdateCityListInDrawerMenu(weather: Weather) {
        drawerMenuPresenter.loadSavedCities()
    }

    override fun onSelect(cityId: String?) {
        drawerLayout.closeDrawer(GravityCompat.START)
        Handler().postDelayed({ cityId?.let { homePagePresenter.loadWeather(it, false) } }, 250)
    }
}
