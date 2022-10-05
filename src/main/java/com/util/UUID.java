package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: Uuid
 * @Date: 2022/10/5 下午 02:59
 * @Author: kalam_au
 * @Description:
 */


public class UUID {
    private static final Logger log = LogManager.getLogger(UUID.class.getName());


    public static String get() {
        return java.util.UUID.randomUUID().toString();
    }
}

