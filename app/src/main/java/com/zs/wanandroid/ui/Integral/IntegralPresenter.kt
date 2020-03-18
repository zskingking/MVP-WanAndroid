package com.zs.wanandroid.ui.Integral

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.IntegralRecordEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * des 积分
 * @author zs
 * @date 2020-03-17
 */
class IntegralPresenter(view:IntegralContract.View):BasePresenter<IntegralContract.View>(view)
,IntegralContract.Presenter<IntegralContract.View>{

    override fun loadData(pageNum:Int) {
        RetrofitHelper.getApiService()
            .getIntegralRecord(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<IntegralRecordEntity>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: IntegralRecordEntity) {
                    view?.showList(t)
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}