package com.zs.wanandroid.ui.search

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zs_wan_android.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.zs.wanandroid.adapter.ArticleAdapter
import com.zs.wanandroid.adapter.OnCollectClickListener
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.ArticleEntity
import com.zs.wanandroid.http.KeyBoardUtil
import com.zs.wanandroid.proxy.DialogProxy
import com.zs.wanandroid.proxy.IConfirmClickCallBack
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.AppManager
import com.zs.wanandroid.utils.PrefUtils
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.utils.UIUtils
import com.zs.wanandroid.weight.ReloadListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.loadingTip
import kotlinx.android.synthetic.main.activity_search.smartRefresh

/**
 * des搜索界面
 * @author zs
 * @date 2020-03-15
 */
class SearchActivity : BaseActivity<SearchContract.Presenter<SearchContract.View>>(),SearchContract.View
    , View.OnClickListener, OnLoadMoreListener, ReloadListener, BaseQuickAdapter.OnItemClickListener,
    OnCollectClickListener {

    private var keyWord: String? = null
    private var rlInitWidth: Int = 0
    private var recordList:MutableList<String>? = null
    /**
     * 如果是第一次加载label，将label隐藏后续动画开启后再显示
     */
    private var isFirstLoad: Boolean = true

    private var pageNum:Int = 0
    private var articleList = mutableListOf<ArticleEntity.DatasBean>()
    private var articleAdapter: ArticleAdapter? = null
    private var currentPosition = 0

    override fun init(savedInstanceState: Bundle?) {
        recordList = if (PrefUtils.getObject(Constants.SEARCH_RECORD)==null){
            mutableListOf()
        }else{
            PrefUtils.getObject(Constants.SEARCH_RECORD) as MutableList<String>?
        }
        initView()
    }

    private fun initView(){
        startSearchAnim()
        loadRecord()
        ivBack.setOnClickListener(this)
        tvClear.setOnClickListener(this)
        ivClear.setOnClickListener(this)
        addSearchListener()

        articleAdapter =
            ArticleAdapter(articleList)
        articleAdapter?.onItemClickListener = this
        articleAdapter?.setCollectClickListener(this)
        articleAdapter?.setNewData(articleList)
        rvSearch.adapter = articleAdapter
        loadingTip.setReloadListener(this)
        smartRefresh?.setOnLoadMoreListener(this)
        rvSearch.layoutManager = LinearLayoutManager(this)
    }

    private fun addSearchListener() {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyWord = editText.text!!.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(keyWord)) {
                    //将已存在的key移除，避免存在重复数据
                    for (index in 0 until recordList?.size!!){
                        if (recordList!![index]==keyWord) {
                            recordList!!.removeAt(index)
                            break
                        }
                    }
                    recordList?.add(keyWord!!)
                    search()
                }
            }else {
                ToastUtils.show("请输入关键字")
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener true
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //搜索框为空的时候显示搜索历史
                if (editText.text!!.toString().isEmpty()) {
                    loadRecord()
                    loadingTip.dismiss()
                    ivClear.visibility = View.GONE
                    smartRefresh.visibility = View.GONE
                    clRecord.visibility = View.VISIBLE
                }else{
                    ivClear.visibility = View.VISIBLE
                    editText.setSelection(editText.text.length)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    /**
     * 加载搜索tag
     */
    private fun loadRecord() {
        labelsView.setLabels(recordList) {label, _, data ->
            if (isFirstLoad) {
                label.visibility = View.GONE
            }
            data
        }
        isFirstLoad = false
        //标签的点击监听
        labelsView.setOnLabelClickListener { _, data, _ ->
            if (data is String) {
                editText.setText(data)
                keyWord = data
                search()
            }
        }
    }

    /**
     * 搜索
     */
    private fun search():Boolean {
        articleList.clear()
        articleAdapter?.setNewData(articleList)
        pageNum = 0
        KeyBoardUtil.closeKeyboard(editText,this)
        keyWord = editText.text!!.toString().trim { it <= ' ' }
        return if (!TextUtils.isEmpty(keyWord)) {
            editText.setText(keyWord)
            smartRefresh.visibility = View.VISIBLE
            clRecord.visibility = View.GONE
            loadingTip.loading()
            presenter?.search(pageNum, keyWord!!)
            true
        } else {
            ToastUtils.show("请输入关键字")
            false
        }
    }

    /**
     * 搜索过渡动画
     */
    private fun startSearchAnim() {
        rlTop.post {
            rlInitWidth = rlTop.measuredWidth
            var left = rlTop.left - UIUtils.dip2px(this, 10f)
            val animator = ValueAnimator.ofInt(rlInitWidth, left)
            animator.duration = 300
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                //获取改变后的值
                val currentValue = animation.animatedValue as Int
                //输出改变后的值
                val params = rlTop.layoutParams as RelativeLayout.LayoutParams
                params.width = currentValue
                rlTop.layoutParams = params
                //展开完毕后开启标签动画，并弹出软键盘
                if (currentValue == left) {
                    startLabelAnim()
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    KeyBoardUtil.openKeyboard(editText, this)
                }
            }
            //启动动画
            animator.start()
        }
    }

    /**
     * 标签展开动画
     * 逐个展开设置可见，并开启动画
     */
    private fun startLabelAnim() {
        for (index in 0 until labelsView.childCount) run {
            val view: View = labelsView.getChildAt(index)
            view.visibility = View.VISIBLE
            val aa = ScaleAnimation(0f, 1f, 0.5f, 1f)
            aa.interpolator = DecelerateInterpolator()
            aa.duration = 500
            view.startAnimation(aa)
        }
    }

    /**
     * 上拉加载
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        keyWord?.let { presenter?.search(pageNum, it) }
    }

    /**
     * 无网络，重新加载
     */
    override fun reload() {
        search()
    }

    /**
     * 查询数据列表
     */
    override fun showList(list: MutableList<ArticleEntity.DatasBean>) {
        dismissLoading()
        if (list.isNotEmpty()){
            articleList.addAll(list)
            articleAdapter?.setNewData(articleList)
        }else {
            if (articleList.size==0)loadingTip.showEmpty()
            else ToastUtils.show("没有数据啦...")
        }
    }

    /**
     * 收藏成功
     */
    override fun collectSuccess() {
        if (currentPosition<articleList.size) {
            articleList[currentPosition].collect = true
            articleAdapter?.notifyItemChanged(currentPosition)
        }
    }

    /**
     * 收藏失败
     */
    override fun unCollectSuccess() {
        if (currentPosition<articleList.size) {
            articleList[currentPosition].collect = false
            articleAdapter?.notifyItemChanged(currentPosition)
        }
    }

    override fun onError(error: String) {
        if (pageNum>0)pageNum--
        dismissLoading()
        ToastUtils.show(error)
    }

    /**
     * 重写finish方法，实现退出动画
     */
    override fun finish() {
        KeyBoardUtil.closeKeyboard(editText,this)
        super.finish()
        val rlWidth = rlTop.measuredWidth
        val animator = ValueAnimator.ofInt(rlWidth, rlInitWidth)
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            //获取改变后的值
            val currentValue = animator.animatedValue as Int
            //输出改变后的值
            val params = rlTop.layoutParams as RelativeLayout.LayoutParams
            params.width = currentValue
            rlTop.layoutParams = params
        }
        animator.start()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        intent(Bundle().apply {
            putString(Constants.WEB_URL,articleList[position].link)
            putString(Constants.WEB_TITLE,articleList[position].title)
        }, WebActivity::class.java,false)
    }

    /**
     * 收藏/取消收藏点击
     */
    override fun onCollectClick(helper: BaseViewHolder, position: Int) {
        if (!AppManager.isLogin()) {
            ToastUtils.show("请先登录")
            return
        }
        if (position<articleList.size){
            //记录当前点击的item
            currentPosition = position
            //收藏状态调用取消收藏接口，反之亦然
            articleList[position].apply {
                if (collect) presenter?.unCollect(id)
                else presenter?.collect(id)
            }
        }
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->finish()
            //清除editText
            R.id.ivClear->editText.setText("")
            //清除搜索记录
            R.id.tvClear -> {
                DialogProxy.confirm(this,"是否确定清除？",object :IConfirmClickCallBack{
                    override fun onClick() {
                        clearRecord()
                    }
                })
            }
        }
    }

    /**
     * 清除搜索记录
     */
    private fun clearRecord(){
        recordList?.clear()
        loadRecord()
    }

    override fun onPause() {
        super.onPause()
        KeyBoardUtil.closeKeyboard(editText,this)
        overridePendingTransition(0, R.anim.search_out_anim)
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefUtils.setObject(Constants.SEARCH_RECORD,recordList)
    }

    override fun createPresenter(): SearchContract.Presenter<SearchContract.View>? {
        return SearchPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }


    override fun getContext(): Context? {
        return this
    }

    /**
     * 隐藏加载
     */
    private fun dismissLoading() {
        loadingTip.dismiss()
        if (smartRefresh.state.isOpening) {
            smartRefresh.finishLoadMore()
            smartRefresh.finishRefresh()
        }
    }

}
