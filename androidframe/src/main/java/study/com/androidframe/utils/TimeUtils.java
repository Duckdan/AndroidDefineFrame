package study.com.androidframe.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 用于当前设备时间和UTC时间的转换
 */

public class TimeUtils {
    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime     UTC时间
     * @param utcFormat   UTC格式
     * @param localFormat 本地时间格式
     * @return
     */
    public static String utc2Local(String utcTime, String utcFormat, String localFormat) {
        if (TextUtils.isEmpty(utcTime)) {
            return "";
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcFormat);//UTC时间格式
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localFormat);//当地时间格式
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * 本地时间转化为UTC时间
     *
     * @param localTime   当前时间
     * @param localFormat 当前时间的格式
     * @param utcFormat   utc时间的格式
     * @param follow      utc时间后面需要跟随的后缀
     * @return
     */
    public static String local2Utc(String localTime, String localFormat, String utcFormat, String follow) {
        if (TextUtils.isEmpty(localTime)) {
            return "";
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localFormat);
        Date localDate = null;
        try {
            localDate = localFormater.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        SimpleDateFormat utcDateFormat = new SimpleDateFormat(utcFormat);
        utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String str = utcDateFormat.format(localDate);
        follow = TextUtils.isEmpty(follow) ? "" : follow;
        return str + follow;
    }

}
