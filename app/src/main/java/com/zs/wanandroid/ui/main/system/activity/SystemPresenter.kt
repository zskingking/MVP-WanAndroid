package com.zs.wanandroid.ui.main.system.activity

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.ArticleEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SystemPresenter(view:SystemContract.View) : BasePresenter<SystemContract.View>(view) ,
    SystemContract.Presenter<SystemContract.View> {


    /**
     * 加载首页文章列表
     */
    override fun loadData(pageNum: Int,cid:Int) {
        RetrofitHelper.getApiService()
            .getSystemArticle(pageNum,cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<ArticleEntity>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: ArticleEntity) {
                    t.datas?.let { view?.showList(it) }
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

            })
    }



    /**
     * 取消收藏
     */
    override fun unCollect(id: Int) {
        RetrofitHelper.getApiService()
            .unCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<Any>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: Any) {
                    view?.unCollectSuccess()
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

            })

    }

    /**
     * 收藏
     */
    override fun collect(id: Int) {
        RetrofitHelper.getApiService()
            .collect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<Any>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: Any) {
                    view?.collectSuccess()
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

            })
    }
}