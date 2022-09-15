package com.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
//@Component("自定义beanName")
@Component
public class IpUtil implements ApplicationListener<WebServerInitializedEvent> {

    static private final Log log = LogFactory.getLog(IpUtil.class);

    public static int Port;
    public static String ip;
    public static String hostName;
    public static String domain;


    public static void main(String[] args) {
        IpUtil ip = new IpUtil();
        Map m = ip.getHostInfo();
        MapUtil mapUtil = new MapUtil();
        MapUtil.iterateMapKeySet(m);
    }

    public static String getHostName() {
        IpUtil u = new IpUtil();
        Map m = u.getHostInfo();
        hostName = (String) m.get("hostName");
        return hostName;
    }


    public Map getHostInfo() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ip = address.getHostAddress();
        hostName = address.getHostName();
        domain = address.getHostName();

        Map m = new HashMap();

        m.put("ip", ip);
        m.put("port", Port);
        m.put("hostName", hostName);
        m.put("domain", domain);

        return m;
    }


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        Port = event.getWebServer().getPort();
    }


}