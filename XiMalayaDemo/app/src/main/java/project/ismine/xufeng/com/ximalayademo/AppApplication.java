package project.ismine.xufeng.com.ximalayademo;

import android.app.Application;

import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;

/**
 * Created by xufeng on 17/9/15.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonRequest.getInstanse().init(this, "02672e53d9bcfd9f8091391166b03efc");
    }
}
