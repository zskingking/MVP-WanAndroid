package com.zs.wanandroid.ui.Integral

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zs_wan_android.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.zs.wanandroid.adapter.IntegralAdapter
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.entity.IntegralRecordEntity
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.activity_integral.*
import kotlinx.android.synthetic.main.activity_integral.ivBack
import kotlinx.android.synthetic.main.activity_integral.loadingTip
import kotlinx.android.synthetic.main.activity_integral.smartRefresh
import android.animation.ValueAnimator
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.IntegralEntity
import com.zs.wanandroid.utils.PrefUtils
import android.view.animation.DecelerateInterpolator


/**
 * des 积分
 * @author zs
 * @date 2020-03-17
 */
class IntegralActivity : BaseActivity<IntegralContract.Presenter<IntegralContract.View>>()
    ,IntegralContract.View , OnLoadMoreListener, ReloadListener {

    private var integralRecordEntity: IntegralRecordEntity? = null;
    private var integralAdapter:IntegralAdapter? = null
    private var integralEntity:IntegralEntity? = null
    private var integralList = mutableListOf<IntegralRecordEntity.DatasBean>()
    private var pageNum = 1

    override fun init(savedInstanceState: Bundle?) {
        PrefUtils.getObject(Constants.INTEGRAL_INFO)?.let {
            integralEntity = PrefUtils.getObject(Constants.INTEGRAL_INFO) as IntegralEntity?
        }
        initView()
        loadingTip.loading()
        loadData()
    }

    private fun initView(){
        //施加阴影和解决RecyclerView滑动冲突
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            llRadius.elevation = 20f
            rvIntegralList.isNestedScrollingEnabled = false
        }
        ivBack.setOnClickListener {
            finish()
        }
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        rvIntegralList.layoutManager = LinearLayoutManager(this)
        integralAdapter = IntegralAdapter(R.layout.item_integral)
        rvIntegralList.adapter = integralAdapter
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData(){
        //banner只加载一次
        integralList.clear()
        integralAdapter?.setNewData(integralList)
        pageNum = 1
        presenter?.loadData(pageNum)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_integral
    }

    override fun showList(t: IntegralRecordEntity) {
        startAnim()
        this.integralRecordEntity = t
        val list = t.datas
        dismissRefresh()
        if (list?.isNotEmpty()!!){
            integralList.addAll(list)
            integralAdapter?.setNewData(integralList)
        }else {
            if (integralList.size==0)loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    override fun onError(error: String) {
        //请求失败将page -1
        if (pageNum>0)pageNum--
        dismissRefresh()
        ToastUtils.show(error)
    }

    /**
     * 开启积分动画
     */
    private fun startAnim(){
        integralEntity?.apply {
            val animator = ValueAnimator.ofInt(0,coinCount)
            //播放时长
            animator.duration = 1500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                //获取改变后的值
                val currentValue = animation.animatedValue as Int
                tvIntegralAnim.text = "$currentValue"
            }
            animator.start()
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        presenter?.loadData(pageNum)
    }

    override fun reload() {
        loadingTip.loading()
        loadData()
    }

    override fun getContext(): Context? {
        return this
    }

    override fun createPresenter(): IntegralContract.Presenter<IntegralContract.View>? {
        return IntegralPresenter(this)
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
