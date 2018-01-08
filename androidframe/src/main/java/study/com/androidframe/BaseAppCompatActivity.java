package study.com.androidframe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;


/**
 * 继承至AppCompatActivity的基类
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealSteepDark();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 添加布局文件
     *
     * @return
     */
    protected abstract int getLayoutView();

    /**
     * 初始化控件
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
