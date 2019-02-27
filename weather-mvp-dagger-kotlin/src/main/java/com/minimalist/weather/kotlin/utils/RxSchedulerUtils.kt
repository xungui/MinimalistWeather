package com.minimalist.weather.kotlin.utils


import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object RxSchedulerUtils {
    /**
     * 在RxJava的使用过程中我们会频繁的调用subscribeOn()和observeOn(),通过Transformer结合
     * Observable.compose()我们可以复用这些代码
     *
     * @return Transformer
     */
    fun <T> normalSchedulersTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer { t ->
            t.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}
