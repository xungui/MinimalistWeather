package com.minimalist.weather.kotlin.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateConvertUtils {

    val DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    val DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
    val DATA_FORMAT_PATTEN_YYYY_MM_DD = "yyyy-MM-dd"

    /**
     * 将时间转换为时间戳
     *
     * @param data             待转换的日期
     * @param dataFormatPatten 待转换日期格式
     */
    fun dateToTimeStamp(data: String?, dataFormatPatten: String): Long {

        val simpleDateFormat = SimpleDateFormat(dataFormatPatten, Locale.CHINA)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(data)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        assert(date != null)
        return date!!.time
    }

    /**
     * 将时间戳转换为日期
     *
     * @param time             待转换的时间戳
     * @param dataFormatPatten 转换出的日期格式
     */
    fun timeStampToDate(time: Long, dataFormatPatten: String): String {

        val simpleDateFormat = SimpleDateFormat(dataFormatPatten, Locale.CHINA)
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    /**
     * 日期转星期
     *
     * @param dateString 日期
     * @return 周一 周二 周三 ...
     */
    fun convertDataToWeek(dateString: String?): String {

        val simpleDateFormat = SimpleDateFormat(DATA_FORMAT_PATTEN_YYYY_MM_DD, Locale.CHINA)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (isNow(date))
            return "今天"

        val weekDaysName = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val calendar = Calendar.getInstance()
        calendar.time = date
        val intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        return weekDaysName[intWeek]
    }

    /**
     * 日期转换
     *
     * @return 08.07
     */
    fun convertDataToString(dateString: String?): String {
        val simpleDateFormat = SimpleDateFormat(DATA_FORMAT_PATTEN_YYYY_MM_DD, Locale.CHINA)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date == null)
            return ""
        val month = if (date.month.toString().length == 1) "0" + date.month
        else date.month.toString()
        val day = if (date!!.day.toString().length == 1) "0" + date.day
        else date.day.toString()
        return "$month.$day"
    }

    /**
     * 判断时间是不是今天
     *
     * @return 是返回true，不是返回false
     */
    private fun isNow(date: Date?): Boolean {
        //当前时间
        val now = Date()
        val simpleDateFormat = SimpleDateFormat(DATA_FORMAT_PATTEN_YYYY_MM_DD, Locale.CHINA)
        //获取今天的日期
        val nowDay = simpleDateFormat.format(now)
        //对比的时间
        val day = simpleDateFormat.format(date)
        return day == nowDay
    }

}
