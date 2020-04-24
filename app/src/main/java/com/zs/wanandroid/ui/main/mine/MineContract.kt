package com.zs.wanandroid.ui.main.mine

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.IntegralEntity

interface MineContract {
    interface View:IBaseView{
        /**
         * 显示积分和用户信息
         */
        fun showIntegral(e: IntegralEntity)
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun loadIntegral()
    }
}