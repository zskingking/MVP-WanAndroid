package com.zs.wanandroid.proxy

import android.app.Dialog
import android.content.Context
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.example.zs_wan_android.R
import com.zs.wanandroid.utils.AppManager


/**
 * des 对话框代理类，对外暴露使用接口，将框架与业务进行隔离
 * @author zs
 * @date 2020-03-12
 */
class DialogProxy {

    companion object{
        private var dialog:Dialog? = null
        /**
         * 二次确认对话框
         */
        fun confirm(context: Context,tips:String,callBack:IConfirmClickCallBack?){
            var dialog:Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm,null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                   .setPositiveButton("确定") { _, _ ->
                       callBack?.onClick()
                       dialog?.dismiss()
                   }.setNegativeButton("取消") { _, _ -> dialog?.dismiss() }
            dialog = builder.create()
            dialog.show()
        }

        /**
         * 提示对话框
         */
        fun tips(context: Context,tips:String,callBack:IConfirmClickCallBack?){
            var dialog:Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm,null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                .setPositiveButton("确定"
                ) { _, _ ->
                    callBack?.onClick()
                    dialog?.dismiss()
                }
            dialog = builder.create()
            dialog.show()
        }

        /**
         * 关于作者
         */
        fun author(context: Context){
            var dialog:Dialog? = null
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_author,null)
            val tvWeChat = view.findViewById<TextView>(R.id.tvWeChat)
            val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
            val tvJs = view.findViewById<TextView>(R.id.tvJs)
            tvWeChat.setOnClickListener {
                AppManager.copy(context,context.getString(R.string.dialog_copy_wechat))
                dialog?.dismiss()
            }
            tvEmail.setOnClickListener {
                AppManager.copy(context,context.getString(R.string.dialog_copy_email))
                dialog?.dismiss()
            }
            tvJs.setOnClickListener {
                AppManager.copy(context,context.getString(R.string.dialog_copy_js))
                dialog?.dismiss()
            }
            builder.setView(view)
            builder.setView(view)
                .setPositiveButton("确定") { _, _ ->
                    dialog?.dismiss()
                }
            dialog = builder.create()

            dialog.show()
        }


        /**
         * 提示对话框
         */
        fun loading(context: Context,tips:String){
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null)
            val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
            tvMsg.text = tips
            builder.setView(view)
            dialog = builder.create()
            dialog?.show()
        }

        fun dismiss(){
            dialog?.dismiss()
        }
    }
}