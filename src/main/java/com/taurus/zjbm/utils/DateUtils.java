package com.taurus.zjbm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author taurus
 * @Date 2022/8/4
 */
public class DateUtils {

    /**
     * 比较时间大下 , date1是否在date2之前 , 之前则返回true 相等或之后返回 false
     *
     * @param date1 入参格式 : 2022-06-07
     * @param date2 入参格式 : 2022-06-08
     * @return bool
     */
    public static boolean checkDate(final String date1, final String date2) {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = simpleDateFormat.parse(date1);
            final Date createDate = simpleDateFormat.parse(date2);
            if (createDate.after(date)) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

}
