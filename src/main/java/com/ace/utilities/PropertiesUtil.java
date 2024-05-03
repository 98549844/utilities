package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Classname: PropertyUtil
 * @Date: 2022/7/28 下午 02:40
 * @Author: kalam_au
 * @Description:
 */

public class PropertiesUtil {
    private static final Logger log = LogManager.getLogger(PropertiesUtil.class.getName());

    //location: src/main/resources/properties/messages.properties
    public static String utilitiesProperties = "properties/utilities.properties";
    public static String messagesProperties = "properties/messages.properties";

    public static String getValueByKey(String propertiesPath, String key) throws IOException {
        log.info("print static properties value");
        Properties properties = new Properties();
        InputStream inStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inStream);
        Console.println(properties.get(key).toString(), Console.BOLD, Console.FLUORESCENT_PURPLE);
        return (String) properties.get(key);
    }


    public static void main(String[] args) throws Exception {
        getValueByKey(utilitiesProperties, "version");
        printProperties(messagesProperties);

    }

    public static void printProperties(String propertiesPath) throws IOException {
        log.info("print static properties");
        Properties properties = new Properties();
        properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesPath));
        for (String key : properties.stringPropertyNames()) {
            Console.println(key + "=" + properties.getProperty(key), Console.BOLD, Console.FLUORESCENT_PURPLE);
        }
    }

}
