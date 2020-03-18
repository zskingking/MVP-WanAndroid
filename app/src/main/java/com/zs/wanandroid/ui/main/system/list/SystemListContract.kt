package com.zs.wanandroid.ui.main.system.list

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.entity.SystemListEntity

/**
 * des体系列表
 * @author zs
 * @date 2020-03-16
 */
interface SystemListContract {

    interface View:IBaseView{
        fun showList(list: MutableList<SystemListEntity>)
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun loadData()
    }
}