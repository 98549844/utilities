import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.BigDecimalUtil;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

public class Utilities {

    private static final Logger log = LogManager.getLogger(Utilities.class.getName());

    public static void main(String[] args) {

       Utilities.getHello();
    }


    public static boolean b() {
        int a = 0;
        return a == 0;

    }

    public static void getHello() {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}", "ACE_UTIL");
        log.error("我是error");
        log.fatal("我是fatal");
        log.trace("退出程序.");
        log.traceExit();
        return;
    }


}
