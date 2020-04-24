package com.example.weather.ui.main

import android.os.Bundle
import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.example.weather.R
import com.example.weather.base.WeatherBaseActivity

/**
 * des 天气预报首页
 * @author zs
 * @date 2020-04-24
 */
class WeatherMainActivity : WeatherBaseActivity<IBasePresenter<IBaseView>>() {
    override fun init(savedInstanceState: Bundle?) {

    }

    override fun createPresenter(): IBasePresenter<IBaseView>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_weather_main
    }


}
