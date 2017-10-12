package project.ismine.xufeng.com.glibaryproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xufeng.android.generalframework.okhttp.OkHttpUtils;
import xufeng.android.generalframework.views.ShapeLoadingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ShapeLoadingDialog s= new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();

        s.show();
    }
}
