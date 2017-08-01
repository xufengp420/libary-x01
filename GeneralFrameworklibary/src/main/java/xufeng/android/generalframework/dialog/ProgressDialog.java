package xufeng.android.generalframework.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import xufeng.android.generalframework.R;
import xufeng.android.generalframework.dialog.progressindicator.AVLoadingIndicatorView;


public class ProgressDialog {
    private Dialog dialog;
    private Context context;
    private AVLoadingIndicatorView lodingView;
    private TextView textLoding;
    private int id = AVLoadingIndicatorView.BallPulse;

    public ProgressDialog(Context context) {
        this.context = context;
    }

    /**
     * 等待 dialog
     */
    public void ShowWaitingDialog() {
        dialog = new Dialog(context, R.style.Dialog);
        dialog.setContentView(R.layout.waiting_dialog_layout);
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        } else {
            return;
        }
        lodingView = (AVLoadingIndicatorView) dialog.findViewById(R.id.lodingView);
        textLoding = (TextView) dialog.findViewById(R.id.textLoding);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);

//        int max = AVLoadingIndicatorView.SemiCircleSpin;
//        int min = 0;
//        Random random = new Random();
//
//        int id = random.nextInt(max) % (max - min + 1) + min;

        lodingView.setIndicatorId(id);
        lodingView.applyAnimation();

        dialog.setCanceledOnTouchOutside(false);

    }

    public void WaitingDialogCancle() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    public void setLodingText(CharSequence text) {
        textLoding.setText(text);
    }

    public boolean isShowing() {
        if (null != dialog) {
            return dialog.isShowing();
        }
        return false;
    }
}
