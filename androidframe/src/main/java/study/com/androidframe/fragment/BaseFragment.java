package study.com.androidframe.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 继承至Fragment的基类
 */

public abstract class BaseFragment extends Fragment {
    protected View view;
    protected Activity activity;
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免重复创建View
        if (view == null) {
            view = inflater.inflate(getLayoutView(), null);
            activity = getActivity();
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    /**
     * 当View创建完成时
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initListener();
        initData();
    }

    protected void jumpActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    protected void jumpActivity(Class<? extends Activity> clazz, String... args) {
        Intent intent = new Intent(activity, clazz);
        for (int i = 0; i < args.length; i++) {
            intent.putExtra(args[i], args[++i]);
        }

        activity.startActivity(intent);
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
