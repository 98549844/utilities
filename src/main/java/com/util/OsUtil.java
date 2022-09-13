package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.Set;

public class OsUtil {
    private final static Logger log = LogManager.getLogger(OsUtil.class.getName());


    public static String getOsInfo() {
        //Java获取当前操作系统的信息
        //https://blog.csdn.net/qq_35981283/article/details/73332040
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").toUpperCase();
        String osVersion = props.getProperty("os.version").toUpperCase();
        String osArch = props.getProperty("os.arch").toUpperCase();
        log.info("OS: {}", osName);
        log.info("OS Version: {}", osVersion);
        log.info("OS architecture: {}", osArch);
        return osName;
    }

    public static String getOsName() {
        return System.getProperties().getProperty("os.name").toUpperCase();
    }


    public static void showAll() {
        Properties pros = System.getProperties();
        Set<Object> keys = pros.keySet();
        for (Object key : keys) {
            System.out.print(key + ": ");
            Console.println(pros.getProperty(key.toString()), Console.BOLD, Console.MAGENTA);
        }

    }

    public static void main(String[] args) {
        getOsInfo();
        showAll();
    }
}
