package com.zs.wanandroid.ui.main.system.list

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.SystemListEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * des体系
 * @author zs
 * @date 2020-03-16
 */
class SystemListPresenter(view:SystemListContract.View):BasePresenter<SystemListContract.View>(view)
    ,SystemListContract.Presenter<SystemListContract.View> {


    override fun loadData() {
        RetrofitHelper.getApiService()
            .getSystemList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :HttpDefaultObserver<MutableList<SystemListEntity>>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: MutableList<SystemListEntity>) {
                    view?.showList(t)
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

            })
    }
}