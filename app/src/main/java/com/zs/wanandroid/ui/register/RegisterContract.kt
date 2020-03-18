package com.zs.wanandroid.ui.register

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView

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