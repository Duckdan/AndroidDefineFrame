package study.com.androidframe.app;

import android.app.Application;

import study.com.androidframe.utils.NetConnectionUtils;

/**
 * 应用入口，Application的基类
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetConnectionUtils.register(this);
    }
}
