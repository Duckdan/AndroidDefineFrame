package study.com.defineandroidframe;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import study.com.androidframe.BaseAppCompatActivity;


public class SecondActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv.setText("成功");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


}
