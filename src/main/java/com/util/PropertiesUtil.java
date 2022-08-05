package com.util;

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

    public static String getPropertyByKey(String propertiesPath, String key) throws IOException {
        Properties properties = new Properties();
        InputStream inStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inStream);
        System.out.println(properties.get(key));
        return (String) properties.get(key);
    }


    public static void main(String[] args) throws Exception {
        getPropertyByKey(utilitiesProperties, "version");
        printProperties(messagesProperties);
    }

    public static void printProperties(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesPath));
        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + "=" + properties.getProperty(key));
        }
    }

}