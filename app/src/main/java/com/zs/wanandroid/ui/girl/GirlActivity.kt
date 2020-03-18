package com.zs.wanandroid.ui.girl

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.zs_wan_android.R
import com.zs.wanandroid.adapter.FragmentListAdapter
import com.zs.wanandroid.base.BaseActivity
import com.zs.wanandroid.base.IBasePresenter
import com.zs.wanandroid.base.IBaseView
import com.zs.wanandroid.constants.Constants
import com.zs.wanandroid.utils.StatusUtils
import kotlinx.android.synthetic.main.activity_girl.*

/**
 * 小姐姐界面
 *
 * @author zs
 * @date 2020-03-15
 */
class GirlActivity : BaseActivity<IBasePresenter<IBaseView>>() {


    override fun init(savedInstanceState: Bundle?) {
        var girlList = mutableListOf<String>()
        var fragmentList = mutableListOf<Fragment>()
        girlList.add(Constants.GIRL1)
        girlList.add(Constants.GIRL2)
        girlList.add(Constants.GIRL3)
        girlList.add(Constants.GIRL4)
        girlList.add(Constants.GIRL5)
        girlList.add(Constants.GIRL6)
        girlList.add(Constants.GIRL7)
        girlList.add(Constants.GIRL8)
        girlList.add(Constants.GIRL9)
        girlList.forEach{
            val fragment = GirlFragment()
            val bundle = Bundle()
            bundle.putString("url", it)
            fragment.arguments = bundle
            fragmentList.add(fragment)
        }
        val adapter = FragmentListAdapter(fragmentList,supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun createPresenter(): IBasePresenter<IBaseView>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_girl
    }


    /**
     * 沉浸式状态
     */
    override fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, true, false)
    }

}
