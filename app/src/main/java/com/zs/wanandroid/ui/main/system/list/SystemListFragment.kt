package com.zs.wanandroid.ui.main.system.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zs_wan_android.R
import com.zs.wanandroid.adapter.OnSystemClickListener
import com.zs.wanandroid.adapter.SystemAdapter
import com.zs.wanandroid.base.LazyFragment
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.entity.SystemListEntity
import com.zs.wanandroid.ui.main.system.activity.SystemActivity
import com.zs.wanandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_system_list.*


/**
 * des 体系
 * @author zs
 * @date 2020-03-16
 */
class SystemListFragment : LazyFragment<SystemListContract.Presenter<SystemListContract.View>>()
    ,SystemListContract.View ,OnSystemClickListener{

    private var systemAdapter:SystemAdapter? = null
    private var systemList: MutableList<SystemListEntity>? = null
    override fun lazyInit() {
        rvSystem.layoutManager = LinearLayoutManager(context)
        systemAdapter = SystemAdapter(R.layout.item_system)
        systemAdapter?.setOnSystemClickListener(this)
        rvSystem.adapter = systemAdapter
        loadingTip.loading()
        presenter?.loadData()
    }

    override fun showList(list: MutableList<SystemListEntity>) {
        systemList = list
        loadingTip.dismiss()
        systemAdapter?.setNewData(list)
    }

    override fun onError(error: String) {
        loadingTip.dismiss()
        ToastUtils.show(error)
    }

    override fun onCollectClick(helper: BaseViewHolder, i: Int, j: Int) {
        val id = systemList?.get(i)?.children?.get(j)?.id
        val title = systemList?.get(i)?.children?.get(j)?.name

        intent(Bundle().apply {
            id?.let { putInt(Constants.SYSTEM_ID, it) }
            putString(Constants.SYSTEM_TITLE, title)
        }, SystemActivity::class.java,false)
    }

    override fun createPresenter():SystemListContract.Presenter<SystemListContract.View> {
        return SystemListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_list
    }

}
