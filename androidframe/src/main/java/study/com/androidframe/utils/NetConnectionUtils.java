package study.com.androidframe.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import study.com.androidframe.enums.NetType;

/**
 * 检测网络连接的工具类
 */
public class NetConnectionUtils {
    private static NetConnectionUtils netConn;
    private Context context;
    private NetType type;

    private NetConnectionUtils(Context context) {
        this.context = context;
    }

    /**
     * 注册网工具管理类
     *
     * @param context
     * @return
     */
    public synchronized static NetConnectionUtils register(Context context) {
        if (netConn == null) {
            netConn = new NetConnectionUtils(context);
        }
        return netConn;
    }

    /**
     * 仅用于获取NetConnectionUtils实例
     *
     * @return
     */
    public static NetConnectionUtils getInstance() {
        return netConn;
    }

    /**
     * 检测网络
     *
     * @return true代表网络连接，false代表网络未连接
     */
    public boolean checkNetWorkState() {
        boolean flag = false;
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            //WIFI已连接,移动数据已连接
            boolean wifiConnected = wifiNetworkInfo.isConnected();
            boolean mobileConnected = dataNetworkInfo.isConnected();
            flag = isFlag(wifiConnected, mobileConnected);
            //API大于23时使用下面的方式进行网络监听
        } else {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放当前网络的连接信息
            boolean[] typeBoolean = new boolean[2];
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                int netType = networkInfo.getType();
                boolean netConnected = networkInfo.isConnected();
                switch (netType) {
                    case ConnectivityManager.TYPE_WIFI:
                        typeBoolean[0] = netConnected ? true : false;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        typeBoolean[1] = netConnected ? true : false;
                        break;
                }
            }
            flag = isFlag(typeBoolean[0], typeBoolean[1]);
        }
        return flag;
    }

    private boolean isFlag(boolean wifiConnected, boolean mobileConnected) {
        boolean flag;
        if (wifiConnected && mobileConnected) {
            type = NetType.ALL;
            flag = true;
            //WIFI已连接,移动数据已断开
        } else if (wifiConnected && !mobileConnected) {
            type = NetType.WIFI;
            flag = true;
            //WIFI已断开,移动数据已连接
        } else if (!wifiConnected && mobileConnected) {
            type = NetType.MOBILE;
            flag = true;
            //WIFI已断开,移动数据已断开
        } else {
            type = NetType.NONE;
            flag = false;
        }
        return flag;
    }

    /**
     * 返回当前设备连接网络的类型
     *
     * @return
     */
    public NetType getNetType() {
        return type;
    }

}
