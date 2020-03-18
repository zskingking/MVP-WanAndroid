package com.zs.wanandroid.adapter

import com.chad.library.adapter.base.BaseViewHolder

/**
 * des 删除回调
 * @author zs
 * @date 2020-03-17
 */
interface OnDeleteClickListener {
    fun onDeleteClick(helper: BaseViewHolder, position: Int)
}