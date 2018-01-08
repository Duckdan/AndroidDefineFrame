package study.com.androidframe.http;

import android.content.Context;

import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import study.com.androidframe.BuildConfig;
import study.com.androidframe.http.interceptor.DefaultCacheControlInterceptor;
import study.com.androidframe.utils.LogUtil;

/**
 * 联网工具类
 */

public class HttpHelper {
    private static HttpHelper helper = null;
    private OkHttpClient okHttpClient;
    private Context context;
    private long timeOut = 3000;
    private Retrofit retrofit;
    private String baseUrl = "";

    private HttpHelper(Context context) {
        this.context = context;
        okHttpClient = createOkHttpClient();
        createRetrofit();
    }

    /**
     * 获取HttpHelper的实例
     *
     * @param context
     * @return
     */
    public synchronized static HttpHelper getInstance(Context context) {
        if (helper == null) {
            helper = new HttpHelper(context);
        }
        return helper;
    }

    /**
     * 设置BaseUrl设置基础地址
     *
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 返回BaseUrl
     *
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置超时时间
     *
     * @param timeOut
     */
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 根据自己的需求自定义OkHttpClient对象，设置OkHttpClient
     * 穿件自己需要的Retrofit实例
     *
     * @param okHttpClient
     */
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        createRetrofit();
    }


    /**
     * 创建OkHttpClient实例
     *
     * @return
     */
    private OkHttpClient createOkHttpClient() {
        //custom OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //timeout
        builder.connectTimeout(timeOut, TimeUnit.SECONDS);
        builder.writeTimeout(timeOut, TimeUnit.SECONDS);
        builder.readTimeout(timeOut, TimeUnit.SECONDS);

        //Log信息拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG) {
                    LogUtil.d("HttpHelper", message);
                }
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logInterceptor);

        //将缓存目录设置当前应用的缓存目录下
        File httpCacheDirectory = new File(context.getCacheDir(), "OkHttpCache");
        builder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        builder.addInterceptor(new DefaultCacheControlInterceptor());

        //使用CookieManager进行Cookie的管理
        CookieManager cookieManager = new CookieManager();
        //设置Cookie管理策略
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        //将Cookie保存在内存中
        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        builder.cookieJar(cookieJar);

        return builder.build();
    }


    /**
     * 创建retrofit实例
     */
    public void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 返回联网请求的服务实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getWebService(Class<T> clazz) {
        T t = retrofit.create(clazz);
        return t;
    }


}
