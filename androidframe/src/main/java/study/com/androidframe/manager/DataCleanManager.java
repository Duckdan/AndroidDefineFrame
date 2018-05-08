package study.com.androidframe.manager;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 数据清理的管理类
 */

public class DataCleanManager {
    public DataCleanManager() {
    }

    /**
     * 清除应用内部的缓存目录
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除应用内部数据库目录
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除应用内部sp文件
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 根据数据库名称，清除应用内部的数据库
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除应用内部file文件夹下面的文件
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部内存的缓存目录
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }

    }

    /**
     * 删除指定路径的文件
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除应用和指定路径的的数据
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath != null) {
            String[] var2 = filepath;
            int var3 = filepath.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String filePath = var2[var4];
                cleanCustomCache(filePath);
            }

        }
    }

    /**
     * 清除应用数据
     *
     * @param context
     */
    public static void cleanApplicationData(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
    }

    /**
     * 根据文件目录删除文件
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        recursionDeleteFile(directory);
    }

    /**
     * 循环删除文件
     *
     * @param file
     */
    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }

                File[] var2 = childFile;
                int var3 = childFile.length;

                for (int var4 = 0; var4 < var3; ++var4) {
                    File f = var2[var4];
                    recursionDeleteFile(f);
                }

                file.delete();
            }

        }
    }

    /**
     * 获取文件的大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0L;

        try {
            File[] e = file.listFiles();

            for (int i = 0; i < e.length; ++i) {
                if (e[i].isDirectory()) {
                    size += getFolderSize(e[i]);
                } else {
                    size += e[i].length();
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return size;
    }

    /**
     * 删除指定文件夹下的文件
     *
     * @param filePath
     * @param deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File e = new File(filePath);
                if (e.isDirectory()) {
                    File[] files = e.listFiles();

                    for (int i = 0; i < files.length; ++i) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }

                if (deleteThisPath) {
                    if (!e.isDirectory()) {
                        e.delete();
                    } else if (e.listFiles().length == 0) {
                        e.delete();
                    }
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024.0D;
        double megaByte = kiloByte / 1024.0D;
        if (megaByte < 1.0D) {
            BigDecimal gigaByte1 = new BigDecimal(Double.toString(kiloByte));
            return gigaByte1.setScale(0, 4).toPlainString() + "KB";
        } else {
            double gigaByte = megaByte / 1024.0D;
            if (gigaByte < 1.0D) {
                BigDecimal teraBytes1 = new BigDecimal(Double.toString(megaByte));
                return teraBytes1.setScale(2, 4).toPlainString() + "MB";
            } else {
                double teraBytes = gigaByte / 1024.0D;
                BigDecimal result4;
                if (teraBytes < 1.0D) {
                    result4 = new BigDecimal(Double.toString(gigaByte));
                    return result4.setScale(2, 4).toPlainString() + "GB";
                } else {
                    result4 = new BigDecimal(teraBytes);
                    return result4.setScale(2, 4).toPlainString() + "TB";
                }
            }
        }
    }

    /**
     * 获取指定文件缓存的大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getCacheSize(File file) throws Exception {
        return getFormatSize((double) getFolderSize(file));
    }
}
