package com.zs.wanandroid.ui.main.tab.list

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.ArticleEntity

/**
 * @author zs
 * @date 2020-03-14
 */
interface TabListContract {
    interface View:IBaseView{
        fun showList(list:MutableList<ArticleEntity.DatasBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
    interface Presenter<T>:IBasePresenter<View>{
        fun loadData(type:Int,id:Int,pageNum:Int)
        fun collect(id:Int)
        fun unCollect(id:Int)
    }
}