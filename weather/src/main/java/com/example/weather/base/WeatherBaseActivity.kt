package com.example.weather.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.base.IBasePresenter

abstract class WeatherBaseActivity<P: IBasePresenter<*>> :BaseActivity<P>() {


}