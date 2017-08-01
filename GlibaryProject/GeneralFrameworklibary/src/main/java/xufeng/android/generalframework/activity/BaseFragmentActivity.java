package xufeng.android.generalframework.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import xufeng.android.generalframework.R;
import xufeng.android.generalframework.control.BaseAppManager;
import xufeng.android.generalframework.dialog.ProgressDialog;
import xufeng.android.generalframework.utils.ImmersionStyleCompat;
import xufeng.android.generalframework.utils.NetWorkUtil;
import xufeng.android.generalframework.utils.StatusBarUtil;

/**
 * Created by Administrator on 2016/9/27.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //APP 管理类
        BaseAppManager.getAppManager().addActivity(BaseFragmentActivity.this);

        progressDialog = new ProgressDialog(this);

        setImmersionStyle();

    }
    /**
     * ----------------------------------------------------------------------
     * --------------------------功能方法-------------------------------------
     * ----------------------------------------------------------------------
     */
    /**
     * 设置普通沉浸样式
     */
    protected void setImmersionStyle() {
        StatusBarUtil statusBarUtil = new StatusBarUtil();
        statusBarUtil.setColor(BaseFragmentActivity.this, getResources().getColor(R.color.statusbar_bg));
    }

    /**
     * Toast
     *
     * @param string
     */
    public void Toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示等待 dialog
     */
    public void showWaitDialog() {
        if (!NetWorkUtil.isNetWorkEnable(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.ShowWaitingDialog();
    }

    /**
     * 消失等待 dialog
     */
    public void dismissWaitDialog() {
        progressDialog.WaitingDialogCancle();
    }

    long exitTime;

    /**
     * 退出
     */
    public void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            Toast("再按一次即可退出应用");
        } else {
            BaseAppManager.getAppManager().AppExit(this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    /**
     * ----------------------------------------------------------------------
     * --------------------------抽象方法-------------------------------------
     * ----------------------------------------------------------------------
     */
}
