package com.zs.wanandroid.ui.main.tab

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.TabEntity

/**
 * @author zs
 * @date 2020-03-14
 */
interface TabContract {
    interface View:IBaseView{
        fun showList(list:MutableList<TabEntity>)
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun loadData(type:Int)
    }
}