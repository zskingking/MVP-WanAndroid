package com.zs.wanandroid.adapter

import com.chad.library.adapter.base.BaseViewHolder

/**
 * 收藏点击事件
 *
 * @author zs
 * @date 2020-03-13
 */
interface OnCollectClickListener {
    fun onCollectClick(helper: BaseViewHolder, position: Int)
}