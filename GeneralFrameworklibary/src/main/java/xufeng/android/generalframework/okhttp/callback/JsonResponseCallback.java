package xufeng.android.generalframework.okhttp.callback;

import android.util.Log;

import com.google.gson.Gson;

import xufeng.android.generalframework.utils.HLog;


/**
 * Created by Administrator on 2016/9/12.
 */
public class JsonResponseCallback implements IResponseCallBack {

    Gson mGson = new Gson();

    @Override
    public <T> T Transform(String response, Class<T> classOfT) {
        HLog.d("TAG", response);
        return mGson.fromJson(response, classOfT);
    }

    public void logE(String tag, String content) {
        int p = 2000;
        long length = content.length();
        if (length < p || length == p)
            Log.e(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, content);
        }
    }
}
