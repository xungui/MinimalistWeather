package com.minimalist.weather.kotlin.feature.contract

/**
 * presenter interface,所有Presenter必须实现此接口
 *
 */
interface BasePresenter<T> {

    fun subscribe()

    fun unSubscribe()

    fun takeView(view: T)

    fun dropView()
}
