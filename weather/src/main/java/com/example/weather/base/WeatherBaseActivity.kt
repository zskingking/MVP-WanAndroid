package com.example.weather.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.base.IBasePresenter

abstract class WeatherBaseActivity<P: IBasePresenter<*>> :BaseActivity<P>() {

    /**
     * 界面跳转
     * @param
     */
    protected fun intent(clazz:Class<*>){
        startActivity(Intent(this,clazz))
    }

    /**
     * 携带bundle跳转
     * @param
     */
    protected fun intent(bundle: Bundle, clazz:Class<*>){
        startActivity(Intent(this, clazz).apply {
            putExtras(bundle)
        })
    }
}