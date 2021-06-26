


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.FileUtil;

import java.io.File;

public class Utilities {

    private static Logger log = LogManager.getLogger(Utilities.class.getName());

    public static void main1(String[] args) {
        String s = "//Users/garlam/IdeaProjects/utilities/src/main/resources/file/image/WechatIMG164.jpeg";
        FileUtil.getFileSize(s);
    }

    public static void getHello() {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}", "ACE_UTIL");
        log.error("我是error");
        log.fatal("我是fatal");

        log.trace("退出程序.");
        log.traceExit();
    }





}
