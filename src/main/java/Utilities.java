import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.BigDecimalUtil;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

public class Utilities {

    private static Logger log = LogManager.getLogger(Utilities.class.getName());

    public static void main(String[] args) {

        BigDecimal b = new BigDecimal(111222333.44);
        if ( new BigDecimal(999999999.99).compareTo(b) == -1) {
        }
    }


    public static boolean b() {
        int a = 0;
        if (a == 0) {
            return true;
        }
        return false;

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
