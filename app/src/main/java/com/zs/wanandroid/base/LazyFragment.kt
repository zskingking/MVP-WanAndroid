package com.zs.wanandroid.base

import android.os.Bundle


/**
 * des 基于androidx实现懒加载
 * @author zs
 * @date 2020-03-14
 */
abstract class LazyFragment<P: IBasePresenter<*>>: BaseFragment<P>() {

    private var isLoaded = false
    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()

}
