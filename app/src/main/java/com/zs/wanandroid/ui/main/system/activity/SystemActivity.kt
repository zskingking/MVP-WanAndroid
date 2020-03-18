package com.zs.wanandroid.ui.main.system.activity

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
import com.zs.wanandroid.adapter.ArticleAdapter
import com.zs.wanandroid.adapter.OnCollectClickListener
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.ArticleEntity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.activity_system.*

class SystemActivity : BaseActivity<SystemContract.Presenter<SystemContract.View>>(),
    SystemContract.View, OnCollectClickListener,
    OnLoadMoreListener, OnRefreshListener, ReloadListener, BaseQuickAdapter.OnItemClickListener {


    private var systemAdapter: ArticleAdapter? = null
    private var systemList = mutableListOf<ArticleEntity.DatasBean>()
    private var currentPosition = 0
    private var pageNum = 0
    private var title:String? = null
    private var cid:Int? = null
    /**
     * 点击取消收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击取消收藏产生的bug
     */
    private var lockCollectClick = true

    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        cid = bundle?.getInt(Constants.SYSTEM_ID)
        title = bundle?.getString(Constants.SYSTEM_TITLE)
        initView()
        loadingTip.loading()
        loadData()
    }

    private fun initView() {
        tvTitle.text = title
        ivBack.setOnClickListener {
            finish()
        }
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnRefreshListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        rvSystem.layoutManager = LinearLayoutManager(this)
        systemAdapter = ArticleAdapter(systemList)
        systemAdapter?.onItemClickListener = this
        systemAdapter?.setCollectClickListener(this)
        rvSystem.adapter = systemAdapter
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData() {
        //banner只加载一次
        systemList.clear()
        systemAdapter?.setNewData(systemList)
        pageNum = 0
        cid?.let { presenter?.loadData(pageNum, it) }
    }

    override fun showList(list: MutableList<ArticleEntity.DatasBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            systemList.addAll(list)
            systemAdapter?.setNewData(systemList)
        } else {
            if (systemList.size == 0) loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    override fun collectSuccess() {
        lockCollectClick = true
        if (currentPosition<systemList.size) {
            systemList[currentPosition].collect = true
            systemAdapter?.notifyItemChanged(currentPosition)
        }
    }

    override fun unCollectSuccess() {
        lockCollectClick = true
        if (currentPosition<systemList.size) {
            systemList[currentPosition].collect = false
            systemAdapter?.notifyItemChanged(currentPosition)
        }
    }

    override fun onError(error: String) {
        lockCollectClick = true
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        ToastUtils.show(error)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        intent(Bundle().apply {
            putString(Constants.WEB_URL, systemList[position].link)
            putString(Constants.WEB_TITLE, systemList[position].title)
        }, WebActivity::class.java, false)
    }

    override fun onCollectClick(helper: BaseViewHolder, position: Int) {
        if (position < systemList.size && lockCollectClick) {
            //点击取消收藏后上锁
            lockCollectClick = false
            //记录当前点击的item
            currentPosition = position
            //取消收藏接口
            presenter?.collect(systemList[position].id)
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        cid?.let { presenter?.loadData(pageNum, it) }
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

    override fun createPresenter(): SystemContract.Presenter<SystemContract.View>? {
        return SystemPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_system
    }

    override fun getContext(): Context? {
        return this
    }
}