package com.zs.wanandroid.ui.collect

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.entity.CollectEntity

/**
 * @author zs
 * @date 2020-03-13
 */
interface CollectContract {
    interface View:IBaseView{
        fun showList(list: MutableList<CollectEntity.DatasBean>)
        fun cancelCollectSuccess()
    }

    interface Presenter<T> :IBasePresenter<View>{
        fun loadData(page:Int)
        fun cancelCollect(id:Int)
    }
}