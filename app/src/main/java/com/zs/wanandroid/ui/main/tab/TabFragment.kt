package com.zs.wanandroid.ui.main.tab

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.zs_wan_android.R
import com.zs.wanandroid.adapter.FragmentListAdapter
import com.zs.wanandroid.adapter.TabAdapter
import com.zs.wanandroid.base.BaseFragment
import com.zs.wanandroid.entity.TabEntity
import com.zs.wanandroid.ui.main.tab.list.TabListFragment
import com.zs.wanandroid.utils.ToastUtils
import com.zs.wanandroid.weight.indicator.OnTabClickListener
import kotlinx.android.synthetic.main.fragment_project.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * 项目/公众号界面
 *
 * @author zs
 * @date 2020-03-14
 */
class TabFragment : BaseFragment<TabContract.Presenter<TabContract.View>>()
    ,TabContract.View ,OnTabClickListener{

    private var tabList = mutableListOf<TabEntity>()
    private var type:Int? = null
    override fun init(savedInstanceState: Bundle?) {
        type = arguments?.getInt("type")
        Log.i(TAG,"type:$type")
        if (VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            flTop.elevation = 10f
        }
        type?.let { presenter?.loadData(it) }
    }

    override fun showList(list: MutableList<TabEntity>) {
        tabList.clear()
        tabList.addAll(list)
        initFragment()
    }

    private fun initFragment(){
        val fragmentList = mutableListOf<Fragment>()
        val list = mutableListOf<String>()
        tabList.forEach {
            val fragment = TabListFragment()
            val bundle = Bundle()
            type?.let { it1 -> bundle.putInt("type", it1) }
            bundle.putInt("id", it.id)
            bundle.putString("name", it.name)
            fragment.arguments = bundle
            fragmentList.add(fragment)
            it.name?.let { it1 -> list.add(it1) }
        }
        val adapter = FragmentListAdapter(fragmentList,childFragmentManager)
        viewPager.offscreenPageLimit = 6
        viewPager.adapter = adapter
        val commonNavigator = CommonNavigator(context)
        val tabAdapter = TabAdapter(list)
        //tab点击事件
        tabAdapter.setOnTabClickListener(this)
        commonNavigator.adapter = tabAdapter
        magicView.navigator = commonNavigator
        //将magicView和viewPager进行绑定
        ViewPagerHelper.bind(magicView,viewPager)
    }

    override fun onError(error: String) {
        ToastUtils.show(error)
    }

    override fun createPresenter(): TabContract.Presenter<TabContract.View>? {
        return TabPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun onTabClick(view: View,index:Int) {
        viewPager.currentItem = index
    }
}
