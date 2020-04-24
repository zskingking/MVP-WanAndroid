package com.example.baselibrary.base
import android.content.Context


interface IBaseView {

    fun getContext():Context?
    fun onError(error:String)
}