package com.zs.wanandroid.ui.search

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.entity.ArticleEntity


/**
 * @author zs
 * @date 2020-03-15
 */
interface SearchContract {
    interface View: IBaseView {
        fun showList(list: MutableList<ArticleEntity.DatasBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }

    interface Presenter<T> : IBasePresenter<View> {
        fun search(pageNum:Int,key:String)
        fun collect(id:Int)
        fun unCollect(id:Int)
    }
}