package com.zs.wanandroid.ui.share

import com.example.baselibrary.base.BasePresenter
import com.zs.wanandroid.event.ShareEvent
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

/**
 * 我的文章
 * @author zs
 * @date 2020-03-17
 */
class SharePresenter(view:ShareContract.View):BasePresenter<ShareContract.View>(view)
    , ShareContract.Presenter<ShareContract.View>{

    override fun share(title: String, link: String) {
        RetrofitHelper.getApiService()
            .shareArticle(title,link)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<Any>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }
                override fun onSuccess(t: Any) {
                    EventBus.getDefault().post(ShareEvent())
                    view?.shareSuccess()
                }
                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}