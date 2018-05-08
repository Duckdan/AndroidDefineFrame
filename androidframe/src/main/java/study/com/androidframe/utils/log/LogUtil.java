//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package study.com.androidframe.utils.log;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import study.com.androidframe.BuildConfig;

public class LogUtil {
    protected static String TAG = "LogUtil";
    protected static final int DEBUG = 0;
    protected static final int BETA = 1;
    protected static final int RELEASE = 2;
    protected static final String LOG_FOLDER_NAME = "WLogs";
    private static int CURRENT_VERSION;

    private LogUtil() {
    }

    public static void setDebugMode(boolean flag) {
        if(flag) {
            CURRENT_VERSION = 0;
        } else {
            CURRENT_VERSION = 2;
        }

    }

    public static void v(String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg));
            default:
        }
    }

    public static void v(String tag, String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(tag, buildMessage(msg));
            default:
        }
    }

    public static void v(String msg, Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(msg, buildMessage(msg), thr);
            default:
        }
    }

    public static void d(String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg));
            default:
        }
    }

    public static void d(String msg, Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(msg, buildMessage(msg), thr);
            default:
        }
    }

    public static void d(String tag, String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(tag, buildMessage(msg));
            default:
        }
    }

    public static void i(String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg));
            default:
        }
    }

    public static void i(String tag, String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(tag, buildMessage(msg));
            default:
        }
    }

    public static void i(String msg, Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg), thr);
            default:
        }
    }

    public static void e(String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg));
            default:
        }
    }

    public static void e(String tag, String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(tag, buildMessage(msg));
            default:
        }
    }

    public static void e(String msg, Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg), thr);
            default:
        }
    }

    public static void w(String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg));
            default:
        }
    }

    public static void w(String tag, String msg) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(tag, buildMessage(msg));
            default:
        }
    }

    public static void w(String msg, Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(msg), thr);
            default:
        }
    }

    public static void w(Throwable thr) {
        switch(CURRENT_VERSION) {
            case 0:
                Log.e(TAG, buildMessage(""), thr);
            default:
        }
    }

    protected static String buildMessage(String msg) {
        return msg;
    }

    public static void f(String msg) {
        f("WLogs", msg);
    }

    public static void f(String folderName, String msg) {
        FileWriter fileWriter = null;
        File logFile = getLogFile(folderName, "logs");

        try {
            fileWriter = new FileWriter(logFile, true);
            fileWriter.append(msg);
            fileWriter.append("\r\n");
            fileWriter.flush();
            CloseUtils.closeQuietly(fileWriter);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private static File getLogFile(String folderName, String fileName) {
        File folder = new File(folderName);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        int newFileCount = 0;
        File existingFile = null;

        File newFile;
        for(newFile = new File(folder, String.format("%s_%s.log", new Object[]{fileName, Integer.valueOf(newFileCount)})); newFile.exists(); newFile = new File(folder, String.format("%s_%s.log", new Object[]{fileName, Integer.valueOf(newFileCount)}))) {
            existingFile = newFile;
            ++newFileCount;
        }

        return existingFile != null?(existingFile.length() >= 2097152L?newFile:existingFile):newFile;
    }

    static {
        CURRENT_VERSION = BuildConfig.DEBUG?0:2;
    }
}
