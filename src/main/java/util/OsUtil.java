package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
		log.info("操作系统称种类：{}", osName);
		return osName;
	}

	public static void main(String[] args) {
		getOsInfo();
	}
}
