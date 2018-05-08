package study.com.androidframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import study.com.androidframe.utils.DialogUtil;
import study.com.androidframe.utils.StatusBarUtil;
import study.com.androidframe.utils.ToastUtils;


/**
 * 继承至AppCompatActivity的基类
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    //标记当前的Activity是否是引导页
    private boolean isSplashActivity = false;
    private boolean isLightStatusBar = false;
    private boolean isTranslucent = false;
    private boolean isNavigation = false;

    public boolean isSplashActivity() {
        return isSplashActivity;
    }

    public void setSplashActivity(boolean splashActivity) {
        isSplashActivity = splashActivity;
    }


    public boolean isLightStatusBar() {
        return isLightStatusBar;
    }

    public void setLightStatusBar(boolean lightStatusBar) {
        isLightStatusBar = lightStatusBar;
    }

    public boolean isTranslucent() {
        return isTranslucent;
    }

    public void setTranslucent(boolean translucent) {
        isTranslucent = translucent;
    }

    public boolean isNavigation() {
        return isNavigation;
    }

    public void setNavigation(boolean navigation) {
        isNavigation = navigation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealSteepDark();
        StatusBarUtil.setStatusBarTranslucent(this, isLightStatusBar, isTranslucent, isNavigation);
        setContentView(getLayoutView());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initListener();
        initData();
    }

    /**
     * 处理沉浸式状态栏，在界面根布局下面通过android:background添加想要的状态栏颜色即可
     * 根布局一定要添加android:fitsSystemWindows="true"这个属性
     */
    protected void dealSteepDark() {
        if (isSplashActivity) { //如果是引导页则当前Activity充满屏幕
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /**
     * 不传参数并且不要结果，直接跳转
     *
     * @param clazz
     */
    public void jumpActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 不传参数，直接跳转
     *
     * @param clazz
     */
    public void jumpActivity(Class clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void jumpActivity(Class<? extends Activity> clazz, String... args) {
        Intent intent = new Intent(this, clazz);
        for (int i = 0; i < args.length; i++) {
            intent.putExtra(args[i], args[++i]);
        }

        startActivity(intent);
    }


    public void showProgressDialog(String message, boolean cancelable) {
        DialogUtil.showProgressDialog(this, message, cancelable);
    }

    public void stopProgressDialog() {
        DialogUtil.stopProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.destroyToast();
    }


    /**
     * 添加布局文件
     *
     * @return
     */
    protected abstract int getLayoutView();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化控件的监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();


}
