package study.com.defineandroidframe;

import android.os.Bundle;
import android.view.View;

import study.com.androidframe.BaseNetActivity;
import study.com.androidframe.event.NetEvent;

public class MainActivity extends BaseNetActivity {

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean setChecked() {
        return true;
    }

    @Override
    protected void defineRefresh(NetEvent event) {

    }

    public void jump(View view) {
        jumpActivity(SecondActivity.class);
    }
}
