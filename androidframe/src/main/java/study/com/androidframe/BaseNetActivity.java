package study.com.androidframe;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import study.com.androidframe.event.NetEvent;
import study.com.androidframe.receiver.NetReceiver;

/**
 * 具有检测网络的Activity
 */

public abstract class BaseNetActivity extends BaseAppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setChecked()) {
            EventBus.getDefault().register(this);
            netReceiver = new NetReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netReceiver, filter);
        }
    }

    /**
     * 在此处进行当前应用是否连接网络的测试
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetEvent event) {
        defineRefresh(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netReceiver != null) {
            EventBus.getDefault().unregister(this);
            unregisterReceiver(netReceiver);
        }
    }

    /**
     * 网络变化时此方法会被调用
     *
     * @param event
     */
    protected abstract void defineRefresh(NetEvent event);

}
