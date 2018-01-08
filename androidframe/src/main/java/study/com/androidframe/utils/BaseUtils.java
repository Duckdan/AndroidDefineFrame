package study.com.androidframe.utils;

import android.os.Environment;

/**
 *基础工具类，用来提供一些极齐通用但是又是一些小功能的工具
 * 例：检测内存卡是否存在，
 */

public class BaseUtils {

    /**
     * 检查当前设备的sd卡是否存在，基本上没有用，现在的手机基本都是内置存储
     *
     * @return
     */
    public static boolean checkExternalStorageIsExists() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
