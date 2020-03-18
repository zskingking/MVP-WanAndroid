package com.zs.wanandroid.ui.my

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.MyArticleEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author zs
 * @date 2020-03-17
 */
class MyArticlePresenter(view:MyArticleContract.View):BasePresenter<MyArticleContract.View>(view)
    ,MyArticleContract.Presenter<MyArticleContract.View> {

    override fun loadData(pageNum: Int) {
        RetrofitHelper.getApiService()
            .getMyArticle(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<MyArticleEntity>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: MyArticleEntity) {
                    view?.showList(t)
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }

    override fun delete(id: Int) {
        RetrofitHelper.getApiService()
            .deleteMyArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<Any>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: Any) {
                    view?.deleteSuccess()
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}