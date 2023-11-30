package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: SqlUtil
 * @Date: 2022/9/16 下午 03:21
 * @Author: kalam_au
 * @Description:
 */


public class SqlUtil {
    private static final Logger log = LogManager.getLogger(SqlUtil.class.getName());


    public static String like(String s) {
        return "%" + s + "%";
    }

    public static String likeLeft(String s) {
        return "%" + s;
    }

    public static String likeRight(String s) {
        return s + "%";
    }
}

