package com.zs.wanandroid.ui.main.tab.list


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
import com.zs.wanandroid.base.LazyFragment
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.ArticleEntity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.AppManager
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.fragment_article_list.loadingTip
import kotlinx.android.synthetic.main.fragment_article_list.smartRefresh

/**
 * des 项目/公众号列表
 *
 * @author zs
 * @date 2020-03-14
 */
class TabListFragment : LazyFragment<TabListContract.Presenter<TabListContract.View>>()
,TabListContract.View,OnCollectClickListener,BaseQuickAdapter.OnItemClickListener
    , OnLoadMoreListener, OnRefreshListener, ReloadListener {

    private var projectList = mutableListOf<ArticleEntity.DatasBean>()
    private var pageNum = 1
    private var projectId : Int = 0
    private var name:String? = null
    private var projectAdapter:ArticleAdapter? = null
    private var currentPosition = 0
    private var type:Int? = null
    override fun lazyInit() {
        type = arguments?.getInt("type")
        projectId = arguments?.getInt("id") ?: 0
        name = arguments?.getString("name") ?: ""
        initView()
        loadingTip.loading()
        loadData()
    }

    private fun initView(){
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnRefreshListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        projectAdapter = ArticleAdapter(projectList)
        projectAdapter?.setCollectClickListener(this)
        projectAdapter?.onItemClickListener = this
        rvProject.layoutManager = LinearLayoutManager(context)
        rvProject.adapter = projectAdapter
    }

    private fun loadData(){
        projectList.clear()
        projectAdapter?.setNewData(projectList)
        pageNum = 1
        type?.let { presenter?.loadData(it,projectId,pageNum) }
    }

    override fun showList(list: MutableList<ArticleEntity.DatasBean>) {
        dismissRefresh()
        loadingTip.dismiss()
        if (list.isNotEmpty()){
            projectList.addAll(list)
            projectAdapter?.setNewData(projectList)
        }else {
            if (projectList.size==0) loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    /**
     * 收藏成功
     */
    override fun collectSuccess() {
        if (currentPosition<projectList.size) {
            projectList[currentPosition].collect = true
            projectAdapter?.notifyItemChanged(currentPosition)
        }
    }

    /**
     * 取消收藏成功
     */
    override fun unCollectSuccess() {
        if (currentPosition<projectList.size) {
            projectList[currentPosition].collect = false
            projectAdapter?.notifyItemChanged(currentPosition)
        }
    }

    override fun onError(error: String) {
        //请求失败将page -1
        if (pageNum>1)pageNum--
        dismissRefresh()
        loadingTip.dismiss()
        ToastUtils.show(error)
    }

    /**
     * 点击收藏
     */
    override fun onCollectClick(helper: BaseViewHolder, position: Int) {
        if (!AppManager.isLogin()) {
            ToastUtils.show("请先登录")
            return
        }
        if (position<projectList.size){
            //记录当前点击的item
            currentPosition = position
            //收藏状态调用取消收藏接口，反之亦然
            projectList[position].apply {
                if (collect) presenter?.unCollect(id)
                else presenter?.collect(id)
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        intent(Bundle().apply {
            putString(Constants.WEB_URL,projectList[position].link)
            putString(Constants.WEB_TITLE,projectList[position].title)
        }, WebActivity::class.java,false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        type?.let { presenter?.loadData(it,id,pageNum) }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        loadData()
    }

    /**
     * 网络出错
     */
    override fun reload() {
        loadingTip.loading()
        loadData()
    }

    override fun createPresenter(): TabListContract.Presenter<TabListContract.View>? {
        return TabListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_article_list
    }

    /**
     * 隐藏刷新加载
     */
    private fun dismissRefresh() {
        if (smartRefresh.state.isOpening) {
            smartRefresh.finishLoadMore()
            smartRefresh.finishRefresh()
        }
    }


}
