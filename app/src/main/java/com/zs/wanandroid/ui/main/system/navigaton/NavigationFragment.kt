package com.zs.wanandroid.ui.main.system.navigaton


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder

import com.example.zs_wan_android.R
import com.zs.wanandroid.adapter.NavigationAdapter
import com.zs.wanandroid.adapter.OnSystemClickListener
import com.zs.wanandroid.base.LazyFragment
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.NavigationEntity
import com.zs.wanandroid.ui.web.WebActivity
import com.zs.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_system_list.*

/**
 * 导航列表
 */
class NavigationFragment  : LazyFragment<NavigationContract.Presenter<NavigationContract.View>>()
    , NavigationContract.View , OnSystemClickListener {

    private var navigationList = mutableListOf<NavigationEntity>()
    private var navigationAdapter: NavigationAdapter? = null

    override fun lazyInit() {
        rvSystem.layoutManager = LinearLayoutManager(context)
        navigationAdapter = NavigationAdapter(R.layout.item_system)
        navigationAdapter?.setOnSystemClickListener(this)
        rvSystem.adapter = navigationAdapter
        loadingTip.loading()
        presenter?.loadData()
    }

    override fun showList(list: MutableList<NavigationEntity>) {
        loadingTip.dismiss()
        navigationList.addAll(list)
        navigationAdapter?.setNewData(navigationList)
    }

    override fun onError(error: String) {
        loadingTip.dismiss()
        ToastUtils.show(error)
    }

    override fun onCollectClick(helper: BaseViewHolder, i: Int, j: Int) {
        intent(Bundle().apply {
            putString(Constants.WEB_URL,navigationList[i].articles[j].link)
            putString(Constants.WEB_TITLE,navigationList[i].articles[j].title)
        }, WebActivity::class.java,false)
    }


    override fun createPresenter(): NavigationContract.Presenter<NavigationContract.View> {
        return NavigationPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_list
    }

}
