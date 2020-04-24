package com.zs.wanandroid.ui.main.system.list

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
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