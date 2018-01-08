package study.com.androidframe.http.interceptor;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import study.com.androidframe.utils.NetConnectionUtils;


/**
 * 默认的缓存拦截器
 */

public class DefaultCacheControlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        NetConnectionUtils netConn = NetConnectionUtils.getInstance();
        if (netConn == null) {
            return null;
        }
        //网络未连接时，强制使用缓存
        if (!netConn.checkNetWorkState()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        //网络连接时
        if (netConn.checkNetWorkState()) {
            int maxAge = 60 * 60; // read from cache for 1 hour
            response.newBuilder()
                    .removeHeader("Pragma")  //移除Pragma
                    //public指对所有内容都进行缓存，max-age指缓存的最大有效时间
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
        return response;
    }
}
