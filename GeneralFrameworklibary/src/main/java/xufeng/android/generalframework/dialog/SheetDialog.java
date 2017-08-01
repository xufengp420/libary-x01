package xufeng.android.generalframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import xufeng.android.generalframework.R;


/**
 * Created by Administrator on 2016/8/26.
 */
public class SheetDialog extends Dialog implements View.OnClickListener {
    private LinearLayout mView;
    private Context context;
    private List<String> items;

    public SheetDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        mView = createView();
    }

    public SheetDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        mView = createView();
    }

    /**
     * 创建基本的背景视图
     */
    private LinearLayout createView() {

        LinearLayout parent = new LinearLayout(context);
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(
                (ViewGroup.LayoutParams.MATCH_PARENT),
                ViewGroup.LayoutParams.MATCH_PARENT);
        parentParams.gravity = Gravity.CENTER;
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(parentParams);
        parent.setGravity(Gravity.CENTER);
        return parent;
    }


    public SheetDialog addItems(String... titles) {
        if (titles == null || titles.length == 0)
            return this;
        items = Arrays.asList(titles);
        createItems();
        return this;
    }

    /**
     * 创建MenuItem
     */
    private void createItems() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        if (items != null && items.size() > 0)
            for (int i = 0; i < items.size(); i++) {
                Button bt = new Button(context);
                bt.setId(i);
                bt.setOnClickListener(this);
                bt.setBackgroundColor(Color.WHITE);
                bt.setText(items.get(i));
                bt.setGravity(Gravity.CENTER);
                bt.setTextColor(Color.parseColor("#1f1f1f"));
                bt.setTextSize(context.getResources().getDimension(R.dimen.x7));

                if (i == 0) {
                    bt.setBackgroundResource(R.drawable.sheet_top_selector);
                } else if (i == items.size() - 1) {
                    bt.setBackgroundResource(R.drawable.sheet_bottom_selector);
                } else {
                    bt.setBackgroundResource(R.drawable.sheet_mid_selector);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) (width * 0.618),
                        (int) ((width * 0.618) * (0.23)));
                mView.addView(bt, params);

                View view = new View(context);
                view.setBackgroundColor(Color.parseColor("#333333"));
                LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
                mView.addView(view, viewParams);
            }
    }

    public void showMenu() {
        show();
        getWindow().setContentView(mView);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v.getId());
    }

    private OnItemClikListener listener;

    public void setOnItemClikListener(OnItemClikListener listener) {
        this.listener = listener;
    }


    public interface OnItemClikListener {

        void onItemClick(int id);

    }
}