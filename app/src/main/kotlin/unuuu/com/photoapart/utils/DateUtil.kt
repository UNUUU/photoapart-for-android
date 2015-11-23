package unuuu.com.photoapart.utils;

import java.text.SimpleDateFormat
import java.util.*

public class DateUtil {
    companion object {
        /**
         * 時間を取得する
         */
        fun stringDateBySimpleDateFormat(format: String, date: Date?): String {
            if (date == null) {
                return ""
            }
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            return sdf.format(date)
        }
    }
}
