package com.zs.wanandroid.ui.main.system.navigaton

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.entity.NavigationEntity


/**
 * des 导航列表
 * @author zs
 * @date 2020-03-16
 */
interface NavigationContract {
    interface View: IBaseView {
        fun showList(list: MutableList<NavigationEntity>)
    }

    interface Presenter<T>: IBasePresenter<View> {
        fun loadData()
    }
}