package com.util;

//import cn.hutool.core.lang.ConsoleTable;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname: HutoolUtil
 * @Date: 2023/9/27 上午 10:03
 * @Author: kalam_au
 * @Description:
 */


public class HutoolUtil {
    private static final Logger log = LogManager.getLogger(HutoolUtil.class.getName());

    public static Set<Class<?>> getAllUtils() {
        return ClassUtil.scanPackage("cn.hutool", (clazz)
                -> !clazz.isInterface() && StrUtil.endWith(clazz.getSimpleName(), "Util"));
    }

    public static void printAllUtils() {
        List<String> header = new ArrayList<>();
        header.add("Utils Name");
        header.add("Name Space");

        List<String[]> body = new ArrayList<>();
        Set<Class<?>> allUtils = getAllUtils();

        for (Class<?> clazz : allUtils) {
            body.add(new String[]{clazz.getSimpleName(), clazz.getPackage().getName()});
        }
        ConsoleTable.println(header, body);
    }

    public static void check(String keyword) {
        String keywordToLowerCase = keyword.toLowerCase();
        List<String> header = new ArrayList<>();
        header.add("Utils Name");
        header.add("Name Space");
        List<String[]> body = new ArrayList<>();

        Set<Class<?>> allUtils = getAllUtils();
        for (Class<?> clazz : allUtils) {
            String simpleName = clazz.getSimpleName();
            String packageName = clazz.getPackage().getName();
            if ((simpleName.toLowerCase().contains(keywordToLowerCase) || packageName.toLowerCase().contains(keywordToLowerCase))) {
                body.add(new String[]{simpleName, packageName});
            }
        }

        if (body.isEmpty()) {
            body.add(new String[]{"No utils found", "***"});
        }

        ConsoleTable.println(header, body);
    }

    public static void main(String[] args) {
        check("util");
        printAllUtils();
    }

}

