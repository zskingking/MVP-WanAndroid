package com.zs.wanandroid.ui.set

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView

interface SetContract {
    interface View:IBaseView{
        fun logoutSuccess()
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun logout()
    }
}