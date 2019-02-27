package com.minimalist.weather.kotlin.widget


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.minimalist.weather.kotlin.R


/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 * 16/12/11
 */
class IndicatorView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private var paint: Paint? = null
    private var markerId: Int = 0
    private var marker: Bitmap? = null
    private var indicatorValue = 0// 默认AQI值
    private var textSize = 6// 默认文字大小
    private var intervalValue = 1// TextView之间的间隔大小，单位dp
    private val textColorId = R.color.indicator_text_color// 默认文字颜色
    private var textColor: Int = 0
    private var indicatorStringsResourceId = R.array.indicator_strings
    private var indicatorColorsResourceId = R.array.indicator_colors
    private var indicatorViewWidth: Int = 0// IndicatorView宽度
    private var paddingTopInXML: Int = 0
    private var indicatorStrings: Array<String>? = null
    private lateinit var indicatorColorIds: IntArray

    private var indicatorValueChangeListener: IndicatorValueChangeListener? = null

    init {
        init(context, attrs)
    }

    /**
     * 控件初始化，构造函数调用
     */
    private fun init(context: Context, attrs: AttributeSet) {
        this.orientation = LinearLayout.HORIZONTAL
        //开启绘图缓存，提高绘图效率
        this.isDrawingCacheEnabled = true

        initPaint()
        initAttrs(attrs)
        fillViewToParent(context)

        this.setWillNotDraw(false)// 确保onDraw()被调用

        this.paddingTopInXML = this.paddingTop
        this.setPadding(this.paddingLeft + this.marker!!.width / 2,
                this.paddingTop + this.marker!!.height,
                this.paddingRight + this.marker!!.width / 2,
                this.paddingBottom)
    }

    /**
     * 初始化paint
     */
    private fun initPaint() {
        this.paint = Paint()
        // 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
        this.paint!!.isAntiAlias = true
        // 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        this.paint!!.isDither = true
    }

    /**
     * 获取自定义attrs
     */
    private fun initAttrs(attrs: AttributeSet) {
        val dm = resources.displayMetrics
        this.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat(), dm).toInt()
        this.intervalValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, intervalValue.toFloat(), dm).toInt()

        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorView)
        this.markerId = typedArray.getResourceId(R.styleable.IndicatorView_marker, R.drawable.ic_vector_indicator_down)
        this.marker = drawableToBitmap(createVectorDrawable(markerId, R.color.indicator_color_1))
        this.indicatorValue = typedArray.getInt(R.styleable.IndicatorView_indicatorValue, indicatorValue)
        this.textSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_textSize, textSize)
        this.intervalValue = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_intervalSize, intervalValue)
        this.textColor = typedArray.getColor(R.styleable.IndicatorView_textColor, resources.getColor(textColorId))
        this.indicatorStringsResourceId = typedArray.getInt(R.styleable.IndicatorView_indicatorStrings, indicatorStringsResourceId)
        this.indicatorColorsResourceId = typedArray.getInt(R.styleable.IndicatorView_indicatorColors, indicatorColorsResourceId)
        typedArray.recycle()
    }

    /**
     * 向父容器中填充View
     */
    private fun fillViewToParent(context: Context) {
        indicatorStrings = context.resources.getStringArray(indicatorStringsResourceId)
        indicatorColorIds = context.resources.getIntArray(indicatorColorsResourceId)
        if (indicatorStrings!!.size != indicatorColorIds.size) {
            throw IllegalArgumentException("qualities和aqiColors的数组长度不一致！")
        }
        for (i in indicatorStrings!!.indices) {
            addTextView(context, indicatorStrings!![i], indicatorColorIds[i])
            if (i != indicatorStrings!!.size - 1) {
                addBlankView(context)
            }
        }
    }

    /**
     * 向父容器中添加TextView
     *
     * @param text  TextView显示文字
     * @param color TextView的背景颜色，如："#FADBCC"
     */
    private fun addTextView(context: Context, text: String, color: Int) {
        val textView = TextView(context)
        textView.setBackgroundColor(color)
        textView.text = text
        textView.setTextColor(textColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        textView.setSingleLine()
        textView.gravity = Gravity.CENTER
        textView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        this.addView(textView)
    }

    /**
     * 向父容器中添加空白View
     */
    private fun addBlankView(context: Context) {
        val transparentView = View(context)
        transparentView.setBackgroundColor(Color.TRANSPARENT)
        transparentView.layoutParams = LinearLayout.LayoutParams(intervalValue, LinearLayout.LayoutParams.WRAP_CONTENT)
        this.addView(transparentView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        this.indicatorViewWidth = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var indicatorViewHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth = indicatorViewWidth + paddingLeft + paddingRight
        val desiredHeight = this.getChildAt(0).measuredHeight + paddingTop + paddingBottom

        //测量宽度
        when (widthMode) {
            View.MeasureSpec.EXACTLY -> {
            }
            View.MeasureSpec.AT_MOST -> indicatorViewWidth = Math.min(desiredWidth, indicatorViewWidth)
            View.MeasureSpec.UNSPECIFIED -> indicatorViewWidth = desiredWidth
        }

        //测量高度
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> {
            }
            View.MeasureSpec.AT_MOST -> indicatorViewHeight = Math.min(desiredHeight, indicatorViewHeight)
            View.MeasureSpec.UNSPECIFIED -> indicatorViewHeight = desiredHeight
        }
        setMeasuredDimension(indicatorViewWidth, indicatorViewHeight)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        drawMarkView(canvas)
    }

    /**
     * 用于绘制指示器图标
     */
    private fun drawMarkView(canvas: Canvas) {

        val width = this.indicatorViewWidth - this.paddingLeft - this.paddingRight - intervalValue * 5

        var left = this.paddingLeft
        if (indicatorValue <= 50) {
            left += indicatorValue * (width * 4 / 6 / 200)
        } else if (indicatorValue > 50 && indicatorValue <= 100) {
            left += indicatorValue * (width * 4 / 6 / 200) + intervalValue
        } else if (indicatorValue > 100 && indicatorValue <= 150) {
            left += indicatorValue * (width * 4 / 6 / 200) + intervalValue * 2
        } else if (indicatorValue > 150 && indicatorValue <= 200) {
            left += indicatorValue * (width * 4 / 6 / 200) + intervalValue * 3
        } else if (indicatorValue > 200 && indicatorValue <= 300) {
            left += width * 4 / 6 + (indicatorValue - 200) * width / 6 / 100 + intervalValue * 4
        } else {
            left += width * 5 / 6 + (indicatorValue - 300) * width / 6 / 200 + intervalValue * 5
        }
        canvas.drawBitmap(marker!!, (left - marker!!.width / 2 - 2).toFloat(), this.paddingTopInXML.toFloat(), paint)
    }

    fun setIndicatorValueChangeListener(indicatorValueChangeListener: IndicatorValueChangeListener) {
        this.indicatorValueChangeListener = indicatorValueChangeListener
    }

    /**
     * 设置空气质量指示值
     */
    fun setIndicatorValue(indicatorValue: Int) {

        if (indicatorValue < 0)
            throw IllegalStateException("参数indicatorValue必须大于0")

        this.indicatorValue = indicatorValue
        if (indicatorValueChangeListener != null) {
            val stateDescription: String
            val indicatorTextColor: Int
            if (indicatorValue <= 50) {
                stateDescription = indicatorStrings!![0]
                indicatorTextColor = indicatorColorIds[0]
            } else if (indicatorValue > 50 && indicatorValue <= 100) {
                stateDescription = indicatorStrings!![1]
                indicatorTextColor = indicatorColorIds[1]
            } else if (indicatorValue > 100 && indicatorValue <= 150) {
                stateDescription = indicatorStrings!![2]
                indicatorTextColor = indicatorColorIds[2]
            } else if (indicatorValue > 150 && indicatorValue <= 200) {
                stateDescription = indicatorStrings!![3]
                indicatorTextColor = indicatorColorIds[3]
            } else if (indicatorValue > 200 && indicatorValue <= 300) {
                stateDescription = indicatorStrings!![4]
                indicatorTextColor = indicatorColorIds[4]
            } else {
                stateDescription = indicatorStrings!![5]
                indicatorTextColor = indicatorColorIds[5]
            }
            marker!!.recycle()
            marker = drawableToBitmap(createVectorDrawable(markerId, indicatorTextColor))
            indicatorValueChangeListener!!.onChange(this.indicatorValue, stateDescription, indicatorTextColor)
        }
        invalidate()
    }

    private fun createVectorDrawable(drawableId: Int, color: Int): Drawable {

        val vectorDrawableCompat = VectorDrawableCompat.create(resources, drawableId, context!!.theme)!!
        DrawableCompat.setTint(vectorDrawableCompat, color)
        DrawableCompat.setTintMode(vectorDrawableCompat, PorterDuff.Mode.SRC_IN)

        return vectorDrawableCompat
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap: Bitmap

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}