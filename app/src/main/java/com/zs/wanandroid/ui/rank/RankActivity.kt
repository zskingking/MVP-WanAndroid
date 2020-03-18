package com.zs.wanandroid.ui.rank

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zs_wan_android.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.zs.wanandroid.adapter.RankAdapter
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.RankEntity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.activity_rank.*


/**
 * des 积分排行榜
 * @author zs
 * @date 2020-03-16
 */
class RankActivity : BaseActivity<RankContract.Presenter<RankContract.View>>(),RankContract.View
    , OnLoadMoreListener, OnRefreshListener, ReloadListener {

    private var rankAdapter: RankAdapter? = null
    private var pageNum = 1
    private var rankList = mutableListOf<RankEntity.DatasBean>()
    /**
     * 我的积分
     */
    private var myIntegral:Int? = null
    /**
     * 我的排名
     */
    private var myRank:Int? = null
    /**
     * 我的名称
     */
    private var myName:String? = null

    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        myIntegral = bundle?.getInt(Constants.MY_INTEGRAL)
        myRank = bundle?.getInt(Constants.MY_RANK)
        myName = bundle?.getString(Constants.MY_NAME)
        initView()
        loadingTip.loading()
        loadData()
    }

    private fun initView(){
        initMyRank()
        ivBack.setOnClickListener {
            finish()
        }
        ivDetail.setOnClickListener {
            intent(Bundle().apply {
                putString(Constants.WEB_URL, Constants.INTEGRAL_RULE)
                putString(Constants.WEB_TITLE,getString(R.string.integral_rule))
            }, WebActivity::class.java,false)
        }
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnRefreshListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        rvRanking.layoutManager = LinearLayoutManager(this)
        rankAdapter = RankAdapter(R.layout.item_rank)
        rvRanking.adapter = rankAdapter
    }

    private fun initMyRank(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            llMyRank.elevation = 10f
            tvIntegral.text = "$myIntegral"
            tvRanking.text = "$myRank"
        }
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData(){
        rankList.clear()
        rankAdapter?.setNewData(rankList)
        pageNum = 1
        presenter?.loadData(pageNum)
    }


    override fun showList(rankEntity: RankEntity) {
        dismissRefresh()
        loadingTip.dismiss()
        if (rankEntity.datas.isNotEmpty()){
            rankList.addAll(rankEntity.datas)
            rankAdapter?.setNewData(rankList)
        }else {
            if (rankList.size==0)loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    override fun onError(error: String) {
        //请求失败将page -1
        if (pageNum>0)pageNum--
        dismissRefresh()
        ToastUtils.show(error)
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


    override fun createPresenter(): RankContract.Presenter<RankContract.View>? {
        return RankPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rank
    }

    override fun getContext(): Context? {
        return this
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
}
