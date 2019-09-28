package com.bzu.gxs.smartrubbish.get;

import java.util.Calendar;

public class getDateSx {

    public static String get(){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "zaoshang";//早上
        } else if (hour >= 8 && hour < 11) {
            return "shangwu";//上午
        } else if (hour >= 11 && hour < 13) {
            return "zhongwu";//中午
        } else if (hour >= 13 && hour < 18) {
            return "xiawu";//下午
        } else {
            return "wanshang";//晚上
        }
    }


}
