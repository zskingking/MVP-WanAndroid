package com.zs.wanandroid.ui.my

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView
import com.zs.wanandroid.entity.MyArticleEntity

/**
 * @author zs
 * @date 2020-03-17
 */
interface MyArticleContract {
    interface View:IBaseView{
        fun showList(t:MyArticleEntity)
        fun deleteSuccess()
    }
    interface Presenter<T>:IBasePresenter<View>{
        fun loadData(pageNum:Int)
        fun delete(id:Int)
    }
}