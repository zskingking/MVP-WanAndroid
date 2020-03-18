package com.zs.wanandroid.utils;

import android.widget.Toast;

import com.zs.wanandroid.WanAndroidApplication;

/**
 * des toast工具类
 * @author zs
 * @date 2020-03-10
 */
public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(WanAndroidApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
