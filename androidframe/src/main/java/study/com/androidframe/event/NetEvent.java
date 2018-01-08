package study.com.androidframe.event;

import android.net.NetworkInfo;

import java.util.HashMap;

/**
 * 传递网络连接状况的Event实例
 */

public class NetEvent {
    /**
     * 当前WIFI和MOBILE网络连接的情况存储在hashMap
     * 键值0对应MOBILE
     * 键值1对应WIFI
     */
    private HashMap<Integer, NetworkInfo> hashMap;
    /**
     * 反应当前设备的联网状态
     * true代表当前设备已连接网络
     * false代表当前设备网络断开
     */
    private boolean isNetConnected;

    public HashMap<Integer, NetworkInfo> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, NetworkInfo> hashMap) {
        this.hashMap = hashMap;
    }

    public boolean isNetConnected() {
        return isNetConnected;
    }

    public void setNetConnected(boolean netConnected) {
        isNetConnected = netConnected;
    }
}
