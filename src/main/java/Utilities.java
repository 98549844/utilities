


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utilities {

    private static Logger log = LogManager.getLogger(Utilities.class.getName());

    public static void main(String[] args) {
        getHello();
    }

    public static void getHello() {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}","ACE_UTIL");
        log.error("我是error");
        log.fatal("我是fatal");

        log.trace("退出程序.");
        log.traceExit();
    }

}
