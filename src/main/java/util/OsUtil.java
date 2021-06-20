package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class OsUtil {
    static private Log log = LogFactory.getLog(JavaUtil.class);

    public static boolean getOsInfo() {
        //Java获取当前操作系统的信息
        //https://blog.csdn.net/qq_35981283/article/details/73332040
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        System.out.println("操作系统称种类：" + props.getProperty("os.name"));
        if (osName != null && osName.toLowerCase().contains("windows")) {
            return true;
        } else {
            return false;
        }
    }
}
