package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: TimerUtil
 * @Date: 2023/3/8 下午 04:12
 * @Author: kalam_au
 * @Description:
 */


public class TimerUtil {
    private static final Logger log = LogManager.getLogger(TimerUtil.class.getName());

    public long start() {
        return System.currentTimeMillis();
    }

    public long end() {
        return System.currentTimeMillis();
    }

    public void calc(long start, long end) {
        Long result = end - start;
        DateTimeUtil.printDateTime(result);
    }

    public void calc(long start) {
        Long result = System.currentTimeMillis() - start;
        DateTimeUtil.printDateTime(result);
    }

}

