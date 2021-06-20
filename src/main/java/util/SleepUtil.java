package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;

public class SleepUtil {
    static private Log log = LogFactory.getLog(SleepUtil.class);

    public static void sleep(Integer second) {
        //休眠一秒钟
        try {
            Long t = second * 1000l;
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        while (true) {
            DateTimeUtil.printCurrentDate();
            SleepUtil.sleep(1);
        }
    }
}
