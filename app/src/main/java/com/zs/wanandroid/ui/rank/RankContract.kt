package com.zs.wanandroid.ui.rank

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.RankEntity

/**
 * des 积分排行榜
 * @author zs
 * @date 2020-03-16
 */
interface RankContract {

    interface View:IBaseView{
        fun showList(rankEntity: RankEntity)
    }

    interface Presenter<T>:IBasePresenter<View>{
        fun loadData(pageNum:Int)
    }
}