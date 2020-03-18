package com.zs.wanandroid.ui.login

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.UserEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.event.LoginEvent
import com.zs.wanandroid.http.RetrofitHelper
import com.zs.wanandroid.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class LoginPresenter(view: LoginContract.View):
    BasePresenter<LoginContract.View>(view)
    ,
    LoginContract.Presenter<LoginContract.View> {


    override fun login(username: String, password: String) {
        RetrofitHelper.getApiService()
            .login(username,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<UserEntity>() {
                override fun onSuccess(t: UserEntity) {
                    //登陆成功保存用户信息，并发送消息
                    PrefUtils.setObject(Constants.USER_INFO,t)
                    EventBus.getDefault().post(LoginEvent())
                    view?.loginSuccess()
                }
                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }

                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }
            })
    }


}
