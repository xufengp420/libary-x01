package xufeng.android.generalframework.utils;

import android.util.Base64;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GsonUtils {

    public static String toJsonStr(Object objcet) {
        Gson gson = new Gson();
        HLog.d("TAG", gson.toJson(objcet));
        return gson.toJson(objcet);
    }

    public static String toBase64Str(String string) {

        return Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
    }
}
