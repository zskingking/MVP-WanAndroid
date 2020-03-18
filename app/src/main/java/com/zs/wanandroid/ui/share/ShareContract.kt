package com.zs.wanandroid.ui.share

import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView

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