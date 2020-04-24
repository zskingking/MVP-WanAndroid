package com.zs.wanandroid.ui.register

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView

/**
 * des 注册
 * @author zs
 * @date 2020-03-18
 */
interface RegisterContract {

    interface View:IBaseView{
        fun registerSuccess()
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun register(username:String,password:String,repassword:String)
    }

}