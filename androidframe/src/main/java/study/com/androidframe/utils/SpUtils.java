package study.com.androidframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import study.com.androidframe.R;

/**
 * sp的工具类
 */

public class SpUtils {
    private static SpUtils spUtils = null;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    private SpUtils(Context context) {
        this.context = context;
        //获取当前应用的名称
        String appName = context.getResources().getString(R.string.app_name);
        sp = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public synchronized static SpUtils registerInstance(Context context) {
        if (spUtils == null) {
            spUtils = new SpUtils(context);
        }
        return spUtils;
    }

    public static SpUtils getInstance() {

        return spUtils;
    }


    /**
     * 添加内容至sp
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    /**
     * 根据key从sp中获取内容
     *
     * @param key
     * @return
     */
    public String getVaule(String key) {
        return sp.getString(key, "");
    }

    /**
     * 清除内容
     *
     * @param key
     */
    public void clear(String key) {
        editor.remove(key).commit();
    }

    public void destroy() {
        spUtils = null;
        sp = null;
        editor = null;
    }
}
