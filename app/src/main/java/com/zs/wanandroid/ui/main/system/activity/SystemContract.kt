package com.zs.wanandroid.ui.main.system.activity

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.ArticleEntity

/**
 * des 体系对应的文章
 * @author zs
 * @date 2020-03-18
 */
interface SystemContract {

    interface View: IBaseView {
        fun showList(list: MutableList<ArticleEntity.DatasBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
    interface Presenter<T> : IBasePresenter<View> {
        fun loadData(pageNum:Int,cid:Int)
        fun collect(id:Int)
        fun unCollect(id:Int)
    }
}