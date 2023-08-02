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

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String get() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String get(String style) {
        String uuid = UUID.get();
        return uuid.replace("-", style == null ? "" : style);
    }

    public static String get(int length) {
        String uuid = UUID.get();
        return uuid.replace("-", "").substring(0, length);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String uuid = UUID.get(15);
            System.out.println(uuid);
            System.out.println(uuid.length());
        }
    }
}

