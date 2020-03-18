package com.zs.wanandroid.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.NestedScrollingChild;


public class CoverTitleBehavior extends CoordinatorLayout.Behavior<View> {

    public CoverTitleBehavior(){

    }

    public CoverTitleBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollingChild;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {


        return true;
    }
}
