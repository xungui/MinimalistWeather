package com.minimalist.weather.kotlin.feature.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.feature.base.BaseRecyclerViewAdapter
import com.minimalist.weather.kotlin.model.data.entity.weather.LifeIndex

class LifeIndexAdapter(private val context: Context, private val indexList: List<LifeIndex>?) : BaseRecyclerViewAdapter<LifeIndexAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeIndexAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_life_index, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: LifeIndexAdapter.ViewHolder, position: Int) {
        val index = indexList!![position]
        holder.indexIconImageView.setImageDrawable(getIndexDrawable(context, index.name!!))
        holder.indexLevelTextView.text = index.index
        holder.indexNameTextView.text = index.name
    }

    override fun getItemCount(): Int {
        return indexList?.size ?: 0
    }

    class ViewHolder(itemView: View, adapter: LifeIndexAdapter) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.index_icon_image_view)
        lateinit var indexIconImageView: ImageView
        @BindView(R.id.index_level_text_view)
        lateinit var indexLevelTextView: TextView
        @BindView(R.id.index_name_text_view)
        lateinit var indexNameTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { v -> adapter.onItemHolderClick(this@ViewHolder) }
        }
    }

    private fun getIndexDrawable(context: Context, indexName: String): Drawable {
        var colorResourceId = R.drawable.ic_index_sunscreen
        if (indexName.contains("防晒")) {
            colorResourceId = R.drawable.ic_index_sunscreen
        } else if (indexName.contains("穿衣")) {
            colorResourceId = R.drawable.ic_index_dress
        } else if (indexName.contains("运动")) {
            colorResourceId = R.drawable.ic_index_sport
        } else if (indexName.contains("逛街")) {
            colorResourceId = R.drawable.ic_index_shopping
        } else if (indexName.contains("晾晒")) {
            colorResourceId = R.drawable.ic_index_sun_cure
        } else if (indexName.contains("洗车")) {
            colorResourceId = R.drawable.ic_index_car_wash
        } else if (indexName.contains("感冒")) {
            colorResourceId = R.drawable.ic_index_clod
        } else if (indexName.contains("广场舞")) {
            colorResourceId = R.drawable.ic_index_dance
        }
        return context.resources.getDrawable(colorResourceId)
    }
}
