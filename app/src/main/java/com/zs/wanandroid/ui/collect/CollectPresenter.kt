package com.zs.wanandroid.ui.collect

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.CollectEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author zs
 * @date 2020-03-13
 */
class CollectPresenter(view:CollectContract.View):BasePresenter<CollectContract.View>(view)
    ,CollectContract.Presenter<CollectContract.View> {


    override fun loadData(page: Int) {
        RetrofitHelper.getApiService()
            .getCollectData(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<CollectEntity>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: CollectEntity) {
                    t.datas?.let { view?.showList(it) }
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

            })
    }

    override fun cancelCollect(id: Int) {
        RetrofitHelper.getApiService()
            .unCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :HttpDefaultObserver<Any>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: Any) {
                    view?.cancelCollectSuccess()
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}