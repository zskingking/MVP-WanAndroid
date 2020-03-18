package com.zs.wanandroid.ui.login

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView

interface LoginContract {
    interface View : IBaseView {
        fun loginSuccess()
    }
    interface Presenter<T>:
        IBasePresenter<View> {
        fun login(username:String,password:String)
    }
}