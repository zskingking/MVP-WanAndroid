package com.zs.wanandroid.weight.indicator;

import android.content.Context;


import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;


public class ZsSimplePagerTitleView extends ColorTransitionPagerTitleView {

    private SelectListener selectListener;
    public ZsSimplePagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        if (selectListener!=null){
            selectListener.onSelect(index,totalCount);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        if (selectListener!=null){
            selectListener.onDeselected(index,totalCount);
        }
    }


    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    public interface SelectListener{
        /**
         * 选中
         * @param index
         * @param totalCount
         */
        void onSelect(int index, int totalCount);

        /**
         * 未选中
         * @param index
         * @param totalCount
         */
        void onDeselected(int index, int totalCount);
    }
}
