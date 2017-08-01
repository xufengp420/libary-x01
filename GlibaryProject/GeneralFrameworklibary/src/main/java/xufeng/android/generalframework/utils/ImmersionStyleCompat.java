package xufeng.android.generalframework.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 设置沉浸Style
 *
 * @author Ming
 * @time 2016-11-17
 */

public class ImmersionStyleCompat {

    static final IStatusBar IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarKitkatImpl();
        } else {
            IMPL = new IStatusBar() {
                @Override
                public void setImmersionStyle(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
                }
            };
        }
    }

    public static void setImmersionStyle(Activity activity, int statusbarcolor, int navigationbarcolor) {
        boolean isLightColor = nearWhite(Color.red(statusbarcolor)) && nearWhite(Color.green(statusbarcolor)) && nearWhite(Color.blue(statusbarcolor));
        setImmersionStyle(activity, statusbarcolor, navigationbarcolor, isLightColor);
    }

    private static boolean nearWhite(int singleColor) {
        return singleColor > 200;
    }

    /**
     * 设置系统状态栏颜色
     *
     * @param activity
     * @param statusbarcolor 状态栏颜色
     * @param lightStatusBar 如果状态栏颜色是浅色，只能对一些设备有效
     */
    public static void setImmersionStyle(Activity activity, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
        setImmersionStyle(activity.getWindow(), statusbarcolor, navigationbarcolor, lightStatusBar);
    }

    /**
     * 设置系统状态栏颜色
     *
     * @param window         the window
     * @param statusbarcolor 状态栏颜色
     * @param lightStatusBar 如果状态栏颜色是浅色，只能对一些设备有效
     */
    public static void setImmersionStyle(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0) {
            return;
        }
        IMPL.setImmersionStyle(window, statusbarcolor, navigationbarcolor, lightStatusBar);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void setFitsSystemWindows(Window window, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                // 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView
                // 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(fitSystemWindows);
            }
        }
    }
}

/**
 * 状态栏接口
 */

interface IStatusBar {
    void setImmersionStyle(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar);
}

/**
 * 兼容MARSHMALLOW及NOUGAT版本
 */

class StatusBarMImpl implements IStatusBar {
    @SuppressLint({"InlinedApi", "NewApi"})
    public void setImmersionStyle(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
        // 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 设置状态栏颜色
        window.setStatusBarColor(statusbarcolor);
        // 设置导航栏颜色
        window.setNavigationBarColor(navigationbarcolor);

        // 设置浅色状态栏时的界面显示
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (lightStatusBar) {
            ui |= 8192;
        } else {
            ui &= ~8192;
        }
        decor.setSystemUiVisibility(ui);

        // 去掉系统状态栏下的windowContentOverlay
        View v = window.findViewById(android.R.id.content);
        if (v != null) {
            //v.setForeground(null);
            //throw new RuntimeException("Stub!");
        }
    }

}

/**
 * 兼容LOLLIPOP版本
 */

class StatusBarLollipopImpl {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void setStatusBarColor(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
        // 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 设置状态栏颜色
        window.setStatusBarColor(statusbarcolor);
        // 设置导航栏颜色
        window.setNavigationBarColor(navigationbarcolor);
    }

}

/**
 * 兼容KITKAT版本
 */

class StatusBarKitkatImpl implements IStatusBar {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setImmersionStyle(Window window, int statusbarcolor, int navigationbarcolor, boolean lightStatusBar) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = getStatusBarHeight(window.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusbarcolor);
        decorViewGroup.addView(statusBarView);
        ImmersionStyleCompat.setFitsSystemWindows(window, true);

        // StatusBarCompatFlavorRom.setLightStatusBar(window,
        // lightStatusBar);原适配miui的，后删
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}