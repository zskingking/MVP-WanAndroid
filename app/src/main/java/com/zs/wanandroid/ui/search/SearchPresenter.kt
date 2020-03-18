package com.zs.wanandroid.ui.search

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.ArticleEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author zs
 * @date 2020-03-15
 */
class SearchPresenter(view: SearchContract.View):
    BasePresenter<SearchContract.View>(view) ,
    SearchContract.Presenter<SearchContract.View> {


    override fun search(pageNum: Int, key: String) {
        RetrofitHelper.getApiService()
            .search(pageNum,key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<ArticleEntity>(){

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
            .subscribe(object : HttpDefaultObserver<Any>(){
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
            .subscribe(object : HttpDefaultObserver<Any>(){
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