package com.zs.wanandroid.ui.main.tab.list

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
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