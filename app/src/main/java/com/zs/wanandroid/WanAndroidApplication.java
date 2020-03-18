package com.zs.wanandroid;

import android.app.Application;
import android.content.Context;

import com.example.zs_wan_android.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zs.wanandroid.utils.ColorUtils;


/**
 * des app
 * @author zs
 * @date 2020-03-05
 */
public class WanAndroidApplication extends Application {
    private static Application baseApplication;
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //指定为经典Footer，默认是 BallPulseFooter
                BallPulseFooter footer = new BallPulseFooter(context);
                footer.setAnimatingColor(ColorUtils.parseColor(R.color.theme));
                footer.setBackgroundColor(ColorUtils.parseColor(R.color.white));
                return footer;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getContext(){
        return baseApplication;
    }
}
