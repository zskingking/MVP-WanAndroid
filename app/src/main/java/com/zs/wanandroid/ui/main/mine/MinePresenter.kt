package com.zs.wanandroid.ui.main.mine

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.IntegralEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import com.zs.wanandroid.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author zs
 * @date 2020-03-12
 */
class MinePresenter(view: MineContract.View):BasePresenter<MineContract.View> (view),
    MineContract.Presenter<MineContract.View> {


    override fun loadIntegral() {
        RetrofitHelper.getApiService()
            .getIntegral()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<IntegralEntity>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: IntegralEntity) {
                    PrefUtils.setObject(Constants.INTEGRAL_INFO,t)
                    view?.showIntegral(t)
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}