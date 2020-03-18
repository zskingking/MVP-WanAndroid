package com.zs.wanandroid.weight.indicator

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator

/**
 * des 将指示器于ViewPager2绑定
 *
 * @author zs
 * @date 2020-03-14
 */
class PagerHelper {
    companion object{
        fun bind(magicIndicator: MagicIndicator,viewPager2: ViewPager2){
            viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    magicIndicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    magicIndicator.onPageSelected(position)
                }

            })
        }
    }
}