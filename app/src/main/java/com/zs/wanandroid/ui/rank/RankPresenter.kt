package com.zs.wanandroid.ui.rank

import com.zs.wanandroid.base.BasePresenter
import com.zs.wanandroid.entity.RankEntity
import com.zs.wanandroid.http.HttpDefaultObserver
import com.zs.wanandroid.http.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * des 积分排行榜
 * @author zs
 * @date 2020-03-16
 */
class RankPresenter(view:RankContract.View):BasePresenter<RankContract.View>(view),RankContract.Presenter<RankContract.View> {

    override fun loadData(pageNum: Int) {
        RetrofitHelper.getApiService()
            .getRank(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<RankEntity>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: RankEntity) {
                    view?.showList(t)
                }

                override fun onError(errorMsg: String) {
                    view?.onError(errorMsg)
                }
            })
    }
}