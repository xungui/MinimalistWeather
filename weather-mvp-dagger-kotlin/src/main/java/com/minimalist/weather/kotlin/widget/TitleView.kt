package com.minimalist.weather.kotlin.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.minimalist.weather.kotlin.R

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 * 2017/8/3
 */
class TitleView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var defaultTitleTextSize = 7// 默认文字大小
    private val defaultTitleBackgroundColorId = R.color.default_title_background_color// 默认背景颜色
    private val defaultTitleTextColorId = R.color.default_title_text_color// 默认文字颜色
    private val defaultTitleLineColorId = R.color.default_title_line_color// 默认底部线条颜色
    private var title: String? = null
    private var titleTextSize: Int = 0
    private var titleTextColor: Int = 0
    private var titleBackgroundColor: Int = 0
    private var titleLineColor: Int = 0

    init {
        initAttrs(context, attrs)
        initView()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val dm = resources.displayMetrics
        this.defaultTitleTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, defaultTitleTextSize.toFloat(), dm).toInt()

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        title = typedArray.getString(R.styleable.TitleView_titleViewText)
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleViewTextSize, defaultTitleTextSize)
        titleTextColor = typedArray.getColor(R.styleable.TitleView_titleViewTextColor, resources.getColor(defaultTitleTextColorId))
        titleBackgroundColor = typedArray.getColor(R.styleable.TitleView_titleViewBackground, resources.getColor(defaultTitleBackgroundColorId))
        titleLineColor = typedArray.getColor(R.styleable.TitleView_titleViewLineColor, resources.getColor(defaultTitleLineColorId))
        typedArray.recycle()
    }

    private fun initView() {

        LayoutInflater.from(context).inflate(R.layout.layout_title_view, this, true)
        this.orientation = LinearLayout.VERTICAL
        this.setBackgroundColor(titleBackgroundColor)

        val titleTextView = findViewById<View>(R.id.title_text_view) as TextView
        titleTextView.text = title
        //        titleTextView.setTextSize(titleTextSize);
        titleTextView.setTextColor(titleTextColor)

        val view = findViewById<View>(R.id.title_line_view)
        view.setBackgroundColor(titleLineColor)
    }
}


