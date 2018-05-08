package study.com.androidframe.utils.log;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/10.
 */

public class CloseUtils {
    private CloseUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException var2) {
                ;
            }
        }

    }

    public static void closeQuietly(Cursor cursor) {
        if(cursor != null) {
            cursor.close();
        }

    }
}
