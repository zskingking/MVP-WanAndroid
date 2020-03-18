package com.zs.wanandroid.utils

import android.content.Context

import com.zs.wanandroid.WanAndroidApplication

/**
 * @author zs
 * @date 2020-03-09
 */
object UIUtils {

    val context: Context get() = WanAndroidApplication.getContext()

    fun dip2px(context: Context, dpValue: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}
