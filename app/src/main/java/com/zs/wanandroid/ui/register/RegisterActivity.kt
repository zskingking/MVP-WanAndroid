package com.zs.wanandroid.ui.register

import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.example.zs_wan_android.R
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.http.KeyBoardUtil
import com.zs.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_register.*


/**
 * @author zs
 * @date
 */
class RegisterActivity : BaseActivity<RegisterContract.Presenter<RegisterContract.View>>()
    ,RegisterContract.View , View.OnClickListener{
    /**
     * 密码是否显示明文
     */
    private var isPasswordShow = false
    /**
     * 确认密码是否显示明文
     */
    private var isRePasswordShow = false

    override fun init(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener(this)
        rlRegister.setOnClickListener(this)
        ivClear.setOnClickListener(this)
        ivPasswordVisibility.setOnClickListener(this)
        ivRePasswordVisibility.setOnClickListener(this)
    }

    override fun registerSuccess() {
        ToastUtils.show("注册成功")
        finish()
    }


    override fun onError(error: String) {
        ToastUtils.show(error)
        setViewStatus(true)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->finish()
            R.id.rlRegister->{
                //关闭软键盘
                KeyBoardUtil.closeKeyboard(etUsername,this)
                KeyBoardUtil.closeKeyboard(etPassword,this)
                KeyBoardUtil.closeKeyboard(etRePassword,this)
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val rePassword = etRePassword.text.toString()
                when {
                    username.isEmpty() -> ToastUtils.show("请输入账号")
                    password.isEmpty() -> ToastUtils.show("请输入密码")
                    rePassword.isEmpty() -> ToastUtils.show("请输入确认密码")
                    password!=rePassword-> ToastUtils.show("两次密码不一致")
                    else -> {
                        setViewStatus(false)
                        presenter?.register(username,password,rePassword)
                    }
                }
            }
            R.id.ivClear-> {
                etUsername.requestFocus()
                etUsername.setText("")
            }
            //密码
            R.id.ivPasswordVisibility->{
                etPassword.requestFocus()
                etPassword.transformationMethod = if (isPasswordShow){
                    isPasswordShow = false
                    //显示密码状态
                    ivPasswordVisibility.setImageResource(R.mipmap.password_show)
                    PasswordTransformationMethod.getInstance()

                }else {
                    isPasswordShow = true
                    //显示明文状态,手动将光标移到最后
                    ivPasswordVisibility.setImageResource(R.mipmap.password_hide)
                    HideReturnsTransformationMethod.getInstance()
                }
                etPassword.setSelection(etPassword.text.length)
            }
            //确认密码
            R.id.ivRePasswordVisibility->{
                etRePassword.requestFocus()
                etRePassword.transformationMethod = if (isRePasswordShow){
                    isRePasswordShow = false
                    //显示密码状态
                    ivRePasswordVisibility.setImageResource(R.mipmap.password_show)
                    PasswordTransformationMethod.getInstance()

                }else {
                    isRePasswordShow = true
                    //显示明文状态,手动将光标移到最后
                    ivRePasswordVisibility.setImageResource(R.mipmap.password_hide)
                    HideReturnsTransformationMethod.getInstance()
                }
                etRePassword.setSelection(etRePassword.text.length)
            }
        }
    }


    /**
     * 注册时给具备点击事件的View上锁，登陆失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus:Boolean){
        rlRegister.isEnabled = lockStatus
        etUsername.isEnabled = lockStatus
        etPassword.isEnabled = lockStatus
        etRePassword.isEnabled = lockStatus
        if (lockStatus) {
            tvRegisterTxt.visibility = View.VISIBLE
            indicatorView.visibility = View.GONE
            indicatorView.hide()
        }else {
            tvRegisterTxt.visibility = View.GONE
            indicatorView.visibility = View.VISIBLE
            indicatorView.show()
        }
    }

    override fun createPresenter(): RegisterContract.Presenter<RegisterContract.View>? {
        return RegisterPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun getContext(): Context? {
        return this
    }

}
