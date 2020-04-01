package com.example.baselibrary

import android.app.Application
import android.content.Context

open class BaseApplication :Application() {


    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object{
        private var baseApplication:BaseApplication? = null

        fun getContext(): Context {
            return baseApplication!!
        }
    }
}