package study.com.androidframe.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import study.com.androidframe.event.NetEvent;
import study.com.androidframe.receiver.NetReceiver;

/**
 * Created by Administrator on 2018/1/8.
 */

public abstract class BaseNetFragment extends BaseFragment {
    /**
     * 监听网络变化
     */
    private NetReceiver netReceiver;

    /**
     * 通过重写此方法可以选择当前界面是否注册网络状态的变化的监听
     * 当前方法返回true则代表监听，返回false则代表不监听
     *
     * @return
     */
    public boolean setChecked() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (setChecked()) {
            EventBus.getDefault().register(this);
            netReceiver = new NetReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            activity.registerReceiver(netReceiver, filter);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetEvent event) {
        defineRefresh(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (netReceiver != null) {
            EventBus.getDefault().unregister(this);
            activity.unregisterReceiver(netReceiver);
        }
    }

    /**
     * 网络变化时此方法会被调用
     *
     * @param event
     */
    protected abstract void defineRefresh(NetEvent event);
}
