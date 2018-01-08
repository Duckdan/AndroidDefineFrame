package study.com.androidframe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import study.com.androidframe.event.NetEvent;

public class NetReceiver extends BroadcastReceiver {

    //用来接收1s之内的广播，避免在1s之内重复通知网络变化情况广播收不到
    private boolean flag = true;
    private HashMap<Integer, NetworkInfo> hashMap = new HashMap<>();
    private NetEvent netEvent = new NetEvent();
    private Handler handler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            //wifi和mobile都连接的情况下，mobile断开是不会影响当前手机的联网状态
            if (info == null) {
                Log.e("receiver", "当前无连接");
                hashMap.clear();
                netEvent.setHashMap(hashMap);
                netEvent.setNetConnected(false);
                EventBus.getDefault().post(netEvent);
                return;
            }
            int type = info.getType();
            //只有移动网络或者WIFI的变化情况才走如下代码
            if (type == 0 || type == 1) {
                hashMap.put(type, info);
                String name = "当前无连接";
                name = info.getTypeName();
                Log.e("receiver", name + "网络变化了" + info.isConnected() + "===" + info.isAvailable() + "对象实例" + info.hashCode());
                if (flag) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("receiver", "网络变化了" + hashMap.toString());
                            netEvent.setHashMap(hashMap);
                            netEvent.setNetConnected(true);
                            EventBus.getDefault().post(netEvent);
                            flag = true;
                        }
                    }, 1000);
                    flag = false; // 避免执行一次等待后，当网络在1s之内发生变化时重复执行
                }
            }
        }
    }
}
