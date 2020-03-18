package com.zs.wanandroid.ui.girl


import android.os.Bundle
import com.example.zs_wan_android.R
import com.zs.wanandroid.base.BaseFragment
import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.proxy.ImageLoad
import kotlinx.android.synthetic.main.fragment_girl.*

/**
 * des 小姐姐界面
 * @author zs
 * @date 2020-03-16
 */
class GirlFragment : BaseFragment<IBasePresenter<*>>() {

    override fun init(savedInstanceState: Bundle?) {
        var url = arguments?.getString("url") ?: ""
        ImageLoad.load(ivGirl,url)
    }

    override fun createPresenter(): IBasePresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_girl
    }


}
