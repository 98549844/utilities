package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OsUtil {
    private final static Logger log = LogManager.getLogger(OsUtil.class.getName());


    public static String getOsInfo() {
        //Java获取当前操作系统的信息
        //https://blog.csdn.net/qq_35981283/article/details/73332040
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").toUpperCase();
        String osVersion = props.getProperty("os.version").toUpperCase();
        String osArch = props.getProperty("os.arch").toUpperCase();
        log.info("操作系统：{}", osName);
        log.info("操作系统版本：{}", osVersion);
        log.info("操作系统架构：{}", osArch);
        return osName;
    }

    public static void main(String[] args) {
        getOsInfo();
    }
}
