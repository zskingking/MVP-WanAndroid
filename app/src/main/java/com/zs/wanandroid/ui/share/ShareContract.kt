package com.zs.wanandroid.ui.share

import com.example.baselibrary.base.IBasePresenter
import com.example.baselibrary.base.IBaseView

/**
 * @author zs
 * @date 2020-03-18
 */
interface ShareContract {
    interface View:IBaseView{
        fun shareSuccess()
    }
    interface Presenter<T>:IBasePresenter<View>{
        fun share(title:String,link:String)
    }
}