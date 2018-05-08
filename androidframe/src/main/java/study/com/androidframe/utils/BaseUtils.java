package study.com.androidframe.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Collection;

/**
 * 基础工具类，用来提供一些极齐通用但是又是一些小功能的工具
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

    /**
     * 检测数值是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmptyArray(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 拍照生成URI
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getExternalFileUri(Context context, File file) {
        Uri contentUri;
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }

        return contentUri;
    }

    public static String getCacheFileRootPath(Context context) {
        return getFileRootPath(context) + "/cache/";
    }

    public static String getFileRootPath(Context context) {
        return Environment.getExternalStorageDirectory() + "/Wstro/" + context.getPackageName();
    }

    /**
     * 获取当前应用的缓存目录
     * @param context
     * @return
     */
    public static String getCacheDir(Context context){
        if(context.getExternalCacheDir() != null){
            return context.getExternalCacheDir().getPath();
        }else{
            return context.getCacheDir().getPath();
        }
    }

}
