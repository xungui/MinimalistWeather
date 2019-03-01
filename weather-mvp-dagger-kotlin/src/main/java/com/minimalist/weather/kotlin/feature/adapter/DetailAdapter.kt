package com.minimalist.weather.kotlin.feature.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.feature.adapter.entity.WeatherDetail
import com.minimalist.weather.kotlin.feature.base.BaseRecyclerViewAdapter

class DetailAdapter(private val details: List<WeatherDetail>?) : BaseRecyclerViewAdapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        val detail = details!![position]
        holder.detailIconImageView.setImageResource(detail.iconResourceId)
        holder.detailKeyTextView.text = detail.key
        holder.detailValueTextView.text = detail.value
    }

    override fun getItemCount(): Int {
        return details?.size ?: 0
    }

    class ViewHolder(itemView: View, adapter: DetailAdapter) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.detail_icon_image_view)
        lateinit var detailIconImageView: ImageView
        @BindView(R.id.detail_key_text_view)
        lateinit var detailKeyTextView: TextView
        @BindView(R.id.detail_value_text_view)
        lateinit var detailValueTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { adapter.onItemHolderClick(this@ViewHolder) }
        }
    }
}
