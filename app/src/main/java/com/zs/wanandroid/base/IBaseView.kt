package com.zs.wanandroid.base

import android.content.Context


interface IBaseView {

    fun getContext():Context?
    fun onError(error:String)
}