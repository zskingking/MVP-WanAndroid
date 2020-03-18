package com.zs.wanandroid.ui.collect

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zs_wan_android.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.zs.wanandroid.adapter.CollectAdapter
import com.zs.wanandroid.adapter.OnCollectClickListener
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.CollectEntity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.activity_collect.ivBack
import kotlinx.android.synthetic.main.activity_collect.loadingTip
import kotlinx.android.synthetic.main.activity_collect.rvCollect
import kotlinx.android.synthetic.main.activity_collect.smartRefresh

/**
 * 收藏
 * @author zs
 * @date 2020-03-13
 */
class CollectActivity : BaseActivity<CollectContract.Presenter<CollectContract.View>>(),CollectContract.View
,OnCollectClickListener,OnLoadMoreListener, OnRefreshListener, ReloadListener, BaseQuickAdapter.OnItemClickListener {

    private var collectAdapter:CollectAdapter? = null
    private var collectList = mutableListOf<CollectEntity.DatasBean>()
    private var currentPosition = 0
    private var pageNum = 0
    /**
     * 点击取消收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击取消收藏产生的bug
     */
    private var lockCollectClick = true
    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadingTip.loading()
        loadData()
    }

    private fun initView(){
        ivBack.setOnClickListener {
            finish()
        }
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnRefreshListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        rvCollect.layoutManager = LinearLayoutManager(this)
        collectAdapter = CollectAdapter(R.layout.item_home_article)
        collectAdapter?.onItemClickListener = this
        collectAdapter?.setCollectClickListener(this)
        rvCollect.adapter = collectAdapter
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData(){
        //banner只加载一次
        collectList.clear()
        collectAdapter?.setNewData(collectList)
        pageNum = 0
        presenter?.loadData(pageNum)
    }

    override fun showList(list: MutableList<CollectEntity.DatasBean>) {
        dismissRefresh()
        if (list.isNotEmpty()){
            collectList.addAll(list)
            collectAdapter?.setNewData(collectList)
        }else {
            if (collectList.size==0)loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    override fun cancelCollectSuccess() {
        lockCollectClick = true
        collectAdapter?.cancelCollect(currentPosition)
    }

    override fun onError(error: String) {
        lockCollectClick = true
        //请求失败将page -1
        if (pageNum>0)pageNum--
        dismissRefresh()
        ToastUtils.show(error)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        intent(Bundle().apply {
            putString(Constants.WEB_URL,collectList[position].link)
            putString(Constants.WEB_TITLE,collectList[position].title)
        }, WebActivity::class.java,false)
    }

    override fun onCollectClick(helper: BaseViewHolder, position: Int) {
        if (position<collectList.size&&lockCollectClick){
            //点击取消收藏后上锁
            lockCollectClick = false
            //记录当前点击的item
            currentPosition = position
            //取消收藏接口
            presenter?.cancelCollect(collectList[position].originId)
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        presenter?.loadData(pageNum)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        loadData()
    }

    override fun reload() {
        loadingTip.loading()
        loadData()
    }


    /**
     * 隐藏刷新加载
     */
    private fun dismissRefresh() {
        loadingTip.dismiss()
        if (smartRefresh.state.isOpening) {
            smartRefresh.finishLoadMore()
            smartRefresh.finishRefresh()
        }
    }

    override fun createPresenter(): CollectContract.Presenter<CollectContract.View>? {
        return CollectPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun getContext(): Context? {
        return this
    }

}
