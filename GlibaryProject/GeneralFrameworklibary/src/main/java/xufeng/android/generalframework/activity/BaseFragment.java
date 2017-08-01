package xufeng.android.generalframework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import xufeng.android.generalframework.dialog.ProgressDialog;
import xufeng.android.generalframework.utils.NetWorkUtil;

/**
 * Created by Administrator on 2016/10/18.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;
    //计算dilaog 被show了几次
    private int showDilaogFlag = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
    }

    /**
     * Toast
     *
     * @param string
     */
    public void Toast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示等待 dialog
     */
    public void showWaitDialog() {
        if (!NetWorkUtil.isNetWorkEnable(getActivity())) {
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        showDilaogFlag++;
        if (!progressDialog.isShowing()) {
            progressDialog.ShowWaitingDialog();
        }
    }

    /**
     * 消失等待 dialog
     */
    public void dismissWaitDialog() {
        showDilaogFlag--;
        showDilaogFlag = showDilaogFlag < 0 ? 0 : showDilaogFlag;
        if (showDilaogFlag == 0 && progressDialog.isShowing()) {
            progressDialog.WaitingDialogCancle();
        }
    }
}
