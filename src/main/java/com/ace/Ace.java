package com.ace;

import com.util.FileUtil;
import com.util.SystemUtil;

import java.io.*;


public class Ace {

    public static void main(String[] args) throws IOException {

        String p = "C:\\ideaPorject\\framework_upgrade\\AceDemoWeb";
        String p1 = "C:\\ideaPorject\\eORSO_schedulejob\\src";
        String p2 = "C:\\ideaPorject\\eORSO_schedulejob\\Template";

        FileUtil.countByType(p, "properties", "java","xhtml","xml");
    }
}

