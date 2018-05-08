package study.com.androidframe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 */

public class ToastUtils {
    private static Toast toast = null;

    /**
     * 用来接收自定义时间的吐司
     *
     * @param context
     * @param msg
     * @param duration
     */
    public static void showToast(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 默认的吐司
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void destroyToast() {
        toast = null;
    }

}
