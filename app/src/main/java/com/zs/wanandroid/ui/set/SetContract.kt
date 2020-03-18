package com.zs.wanandroid.ui.set

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView

interface SetContract {
    interface View:IBaseView{
        fun logoutSuccess()
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun logout()
    }
}