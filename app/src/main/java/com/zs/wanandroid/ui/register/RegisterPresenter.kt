package com.zs.wanandroid.ui.register

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * des 注册
 * @author zs
 * @date 2020-03-18
 */
class RegisterPresenter(view:RegisterContract.View):BasePresenter<RegisterContract.View>(view)
    ,RegisterContract.Presenter<RegisterContract.View> {

    override fun register(username: String, password: String, repassword: String) {
        RetrofitHelper.getApiService()
            .register(username,password,repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<Any>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: Any) {
                    view?.registerSuccess()
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}