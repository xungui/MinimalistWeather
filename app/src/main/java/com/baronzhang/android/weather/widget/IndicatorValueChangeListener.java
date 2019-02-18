package com.baronzhang.android.weather.widget;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public interface IndicatorValueChangeListener {

    void onChange(int currentIndicatorValue, String stateDescription, int indicatorTextColor);
}
