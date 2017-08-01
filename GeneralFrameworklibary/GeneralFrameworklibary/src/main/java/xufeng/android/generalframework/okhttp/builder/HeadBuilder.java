package xufeng.android.generalframework.okhttp.builder;


import xufeng.android.generalframework.okhttp.OkHttpUtils;
import xufeng.android.generalframework.okhttp.request.OtherRequest;
import xufeng.android.generalframework.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
