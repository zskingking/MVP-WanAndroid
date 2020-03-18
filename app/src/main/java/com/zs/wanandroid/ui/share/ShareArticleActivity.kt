package com.zs.wanandroid.ui.share

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.zs_wan_android.R
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.proxy.DialogProxy
import com.zs.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_share_articel.*

/**
 * 分享文章
 * @author zs
 * @date 2020-03-18
 */
class ShareArticleActivity : BaseActivity<ShareContract.Presenter<ShareContract.View>>(),
    ShareContract.View, View.OnClickListener {


    override fun init(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener(this)
        btShare.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->finish()
            R.id.btShare->{
                DialogProxy.loading(this,"正在分享")
                val title = etTitle.text.toString()
                val link = etLink.text.toString()
                presenter?.share(title,link)
            }
        }
    }

    override fun shareSuccess() {
        DialogProxy.dismiss()
        finish()
    }


    override fun onError(error: String) {
        DialogProxy.dismiss()
        ToastUtils.show(error)
    }



    override fun createPresenter(): ShareContract.Presenter<ShareContract.View>? {
        return SharePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_share_articel
    }

    override fun getContext(): Context? {
        return this
    }


}
