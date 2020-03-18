package com.zs.wanandroid.ui.main.system

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.zs_wan_android.R
import com.zs.wanandroid.adapter.FragmentListAdapter
import com.zs.wanandroid.adapter.TabAdapter
import com.zs.wanandroid.base.BaseFragment
import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.ui.main.system.list.SystemListFragment
import com.zs.wanandroid.ui.main.system.navigaton.NavigationFragment
import com.zs.wanandroid.weight.indicator.OnTabClickListener
import kotlinx.android.synthetic.main.fragment_system.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * des 体系
 * @author zs
 * @date 2020-03-16
 */
class SystemFragment : BaseFragment<IBasePresenter<*>>(),OnTabClickListener {


    private var tabList = mutableListOf<String>()

    override fun init(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            llTop.elevation = 10f
        }
        tabList.add(Constants.SYSTEM_1)
        tabList.add(Constants.SYSTEM_2)
        initFragment()
    }

    private fun initFragment(){
        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(SystemListFragment())
        fragmentList.add(NavigationFragment())
        val adapter = FragmentListAdapter(fragmentList,childFragmentManager)
        viewPager.offscreenPageLimit = 6
        viewPager.adapter = adapter
        val commonNavigator = CommonNavigator(context)
        val tabAdapter = TabAdapter(tabList)
        //tab点击事件
        tabAdapter.setOnTabClickListener(this)
        commonNavigator.adapter = tabAdapter
        magicView.navigator = commonNavigator
        //将magicView和viewPager进行绑定
        ViewPagerHelper.bind(magicView,viewPager)
    }

    override fun onTabClick(view: View, index: Int) {
        viewPager.currentItem = index
    }

    override fun createPresenter(): IBasePresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

}
