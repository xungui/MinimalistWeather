package com.minimalist.weather.kotlin.feature.base

import android.support.v7.widget.RecyclerView
import android.widget.AdapterView

abstract class BaseRecyclerViewAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    var onItemClickListener: AdapterView.OnItemClickListener? = null


    protected fun onItemHolderClick(itemHolder: RecyclerView.ViewHolder) {
        onItemClickListener?.onItemClick( null, itemHolder.itemView,
                itemHolder.adapterPosition, itemHolder.itemId)
    }

}
