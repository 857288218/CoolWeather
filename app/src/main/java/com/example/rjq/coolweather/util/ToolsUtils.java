package com.example.rjq.coolweather.util;

import android.view.View;
import android.view.ViewGroup;

import com.example.rjq.coolweather.base.BaseApplication;

public class ToolsUtils {
    /**
     * 获取状态栏高度
     *
     * @return px
     */
    public static int getStatusBarHeightPX() {
        int statusBarHeight = 0;
        int resourceId = BaseApplication.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = BaseApplication.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;//px
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
