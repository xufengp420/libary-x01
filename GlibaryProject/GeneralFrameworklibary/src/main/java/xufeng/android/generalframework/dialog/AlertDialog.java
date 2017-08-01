package xufeng.android.generalframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spanned;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import xufeng.android.generalframework.R;

/**
 * Created by Administrator on 2016/11/10.
 */

/**
 * 1、AlertDialog()构造器中获取手机参数
 * 2、builder()中获取控件、填充布局、设置长宽
 * 3、setTitle、setMsg、setCancelable、setPositiveButton、setNegativeButton
 * 4、在show()方法中setLayout设置布局文件即可
 */
public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private ScrollView scrollView;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    /**
     * 参数为context 用来获取手机参数！！！
     *
     * @param context
     */
    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.alert_dialog_layout, null);

        // 根布局id
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        // 头部
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        // 消息体
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        // 取消
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        // 退出
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        // 按钮中间的线段
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        // 填充Dialog布局！！！
        dialog.setContentView(view);


        // 调整dialog背景大小 LinearLayout.setLayoutParams
        // 必须使用FrameLayout.LayoutParams设置参数
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.7), FrameLayout.LayoutParams.WRAP_CONTENT));

        lLayout_bg.setMinimumHeight((int) (display
                .getWidth() * 0.7 * 0.5));

        return this;
    }

    /**
     * 头部标题
     *
     * @param title
     * @return
     */
    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_msg.setVisibility(View.GONE);
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    /**
     * 消息体
     *
     * @param msg
     * @return
     */
    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            lLayout_bg.setMinimumHeight(0);
            scrollView.setVisibility(View.GONE);
            txt_msg.setVisibility(View.GONE);
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    /**
     * 消息体
     *
     * @param msg
     * @return
     */
    public AlertDialog setMsg(String msg, int gravity) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        txt_msg.setGravity(gravity);
        return this;
    }

    /**
     * 消息体
     *
     * @param msg
     * @return
     */
    public AlertDialog setMsg(Spanned msg, int gravity) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        txt_msg.setGravity(gravity);
        return this;
    }

    /**
     * 取消窗口
     *
     * @param cancel
     * @return
     */
    public AlertDialog setCancelable(boolean cancel) {

        // dialog.setCancelable(cancel);或者用这个方法
        dialog.cancel();
        return this;
    }

    /**
     * 确认按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public AlertDialog setPositiveButton(String text,
                                         final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.cancel();
            }
        });
        return this;
    }

    /**
     * 取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public AlertDialog setNegativeButton(String text,
                                         final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.cancel();
            }
        });
        return this;
    }

    /**
     * 设置布局文件
     */
    private void setLayout() {

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        } else {
            img_line.setVisibility(View.GONE);
            if (showPosBtn) {
                btn_pos.setVisibility(View.VISIBLE);
                btn_pos.setBackgroundResource(R.drawable.alertdialog_mid_selector);
            }
            if (showNegBtn) {
                btn_neg.setVisibility(View.VISIBLE);
                btn_neg.setBackgroundResource(R.drawable.alertdialog_mid_selector);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean bool) {
        dialog.setCanceledOnTouchOutside(bool);

    }

    public void setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return onKeyListener.onKey(dialog, keyCode, event);
            }
        });
    }

    /**
     * 显示dialog视图
     */
    public void show() {
        setLayout();
        dialog.show();
    }
}
