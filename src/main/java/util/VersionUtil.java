package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

/**
 * @Classname: SpringUtil
 * @Date: 2022/8/4 上午 11:44
 * @Author: kalam_au
 * @Description:
 */


public class VersionUtil {
    private static final Logger log = LogManager.getLogger(VersionUtil.class.getName());


    public static void main(String[] args) {
        showSpringFrameworkVersion();
        showSpringbootVersion();
    }

    public static void showSpringFrameworkVersion() {
        Console.println("Spring Framework Version: " + SpringVersion.getVersion(), Console.BOLD);
    }

    public static void showSpringbootVersion() {
        Console.println("Spring Boot Version: " + SpringBootVersion.getVersion(), Console.BOLD);
    }

}

