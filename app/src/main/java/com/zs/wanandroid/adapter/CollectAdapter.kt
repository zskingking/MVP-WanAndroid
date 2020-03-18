package com.zs.wanandroid.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zs_wan_android.R
import com.zs.wanandroid.entity.CollectEntity
import com.zs.wanandroid.weight.OnLimitClickHelper
import com.zs.wanandroid.weight.OnLimitClickListener

/**
 * des 收藏适配器
 *
 * @author zs
 * @date 2020-03-13
 */
class CollectAdapter(layoutId:Int):BaseQuickAdapter<CollectEntity.DatasBean,BaseViewHolder>(layoutId){

    private var collectClickListener:OnCollectClickListener? = null

    fun setCollectClickListener(collectClickListener:OnCollectClickListener){
        this.collectClickListener = collectClickListener
    }
    override fun convert(helper: BaseViewHolder, item: CollectEntity.DatasBean?) {
        item?.run {
            helper.setText(R.id.tvTag,"")
            helper.setText(R.id.tvAuthor,author)
            helper.setText(R.id.tvDate,niceDate)
            helper.setText(R.id.tvTitle,title)
            helper.setText(R.id.tvChapterName,chapterName)
            helper.getView<ImageView>(R.id.ivCollect)
                .apply {
                    setImageResource(R.mipmap.article_collect)
                    setOnClickListener(OnLimitClickHelper(OnLimitClickListener {
                        collectClickListener?.onCollectClick(helper,helper.adapterPosition)
                    }))
                }
        }
    }

    /**
     * 取消收藏，做单个删除
     */
    fun cancelCollect(position:Int){
        data.removeAt(position)
        notifyItemRemoved(position)
    }

}