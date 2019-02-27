package com.minimalist.weather.kotlin.view.contract

/**
 * presenter interface,所有Presenter必须实现此接口
 *
 */
interface BasePresenter {

    fun subscribe()

    fun unSubscribe()
}
