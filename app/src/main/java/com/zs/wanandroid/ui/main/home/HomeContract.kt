package com.zs.wanandroid.ui.main.home

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.entity.BannerEntity
import com.zs.wanandroid.entity.ArticleEntity

interface HomeContract {
    interface View : IBaseView {
        fun showList(list: MutableList<ArticleEntity.DatasBean>)
        fun showBanner(bannerList:MutableList<BannerEntity>)
        fun collectSuccess()
        fun unCollectSuccess()
    }

    interface Presenter<T> : IBasePresenter<View> {
        fun loadData(pageNum:Int)
        fun loadBanner()
        fun collect(id:Int)
        fun unCollect(id:Int)
    }
}