package com.zs.wanandroid.ui.set

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.zs_wan_android.R
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.proxy.DialogProxy
import com.zs.wanandroid.proxy.IConfirmClickCallBack
import com.zs.wanandroid.ui.login.LoginActivity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.AppManager
import com.zs.wanandroid.utils.CacheDataManager
import com.zs.wanandroid.utils.StatusUtils
import com.zs.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_set.*

class SetActivity : BaseActivity<SetContract.Presenter<SetContract.View>>(),SetContract.View ,
    View.OnClickListener {


    override fun init(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener(this)
        tvClear.setOnClickListener(this)
        tvVersion.setOnClickListener(this)
        tvAuthor.setOnClickListener(this)
        tvProject.setOnClickListener(this)
        tvCopyright.setOnClickListener(this)
        tvLogout.setOnClickListener(this)
        tvClearValue.text = CacheDataManager.getTotalCacheSize(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            //清除缓存
            R.id.ivBack->finish()
            //清除缓存
            R.id.tvClear->{
                DialogProxy.confirm(this,"缓存中可能包含小姐姐照片哦，是否确定清除?",object : IConfirmClickCallBack {
                    override fun onClick() {
                        CacheDataManager.clearAllCache(this@SetActivity)
                        tvClearValue.text = ""
                        ToastUtils.show("已清除")
                    }
                })
            }
            //版本
            R.id.tvVersion-> ToastUtils.show("已是最新版本")
            //关于作者
            R.id.tvAuthor->{
                DialogProxy.author(this)
            }
            //项目
            R.id.tvProject->{
                intent(Bundle().apply {
                    putString(Constants.WEB_URL,Constants.APP_GITHUB)
                    putString(Constants.WEB_TITLE,Constants.APP_NAME)
                }, WebActivity::class.java,false)
            }
            //版权
            R.id.tvCopyright->{
                DialogProxy.tips(this,Constants.COPYRIGHT,object : IConfirmClickCallBack {
                    override fun onClick() {
                    }
                })
            }
            //退出登录
            R.id.tvLogout->{
                DialogProxy.confirm(this,"是否确定退出?",object : IConfirmClickCallBack {
                    override fun onClick() {
                        presenter?.logout()
                    }
                })

            }
        }
    }

    override fun createPresenter(): SetContract.Presenter<SetContract.View>? {
        return SetPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_set
    }

    /**
     * 退出登陆成功，清除用户状态
     */
    override fun logoutSuccess() {
        finish()
        ToastUtils.show("已退出登录")
        AppManager.resetUser()
        intent(LoginActivity::class.java,false)
    }

    override fun getContext(): Context? {
        return this
    }

    override fun onError(error: String) {
        ToastUtils.show(error)
    }

    /**
     * 设置状态栏背景颜色
     */
    override fun setStatusColor() {
        StatusUtils.setUseStatusBarColor(this, com.zs.wanandroid.utils.ColorUtils.parseColor("#ffffff"))
    }



}
