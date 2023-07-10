package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Classname: CurlUtil
 * @Date: 2023/7/10 下午 06:22
 * @Author: kalam_au
 * @Description:
 */


public class CurlUtil {
    private static final Logger log = LogManager.getLogger(CurlUtil.class.getName());

    public static void main(String[] args) {
        String url = "https://api.github.com/users/octocat";
        String response = sendGetRequest(url);
        System.out.println(response);
    }

    public static String sendGetRequest(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

}

