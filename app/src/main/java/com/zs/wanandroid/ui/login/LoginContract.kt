package com.zs.wanandroid.ui.login

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView

interface LoginContract {
    interface View : IBaseView {
        fun loginSuccess()
    }
    interface Presenter<T>:
        IBasePresenter<View> {
        fun login(username:String,password:String)
    }
}