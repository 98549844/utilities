package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: CodeUtil
 * @Date: 23/2/2022 9:25 PM
 * @Author: garlam
 * @Description:
 */


public class CodeUtil {
    private static final Logger log = LogManager.getLogger(CodeUtil.class.getName());

    static String path = "C:\\ideaPorject\\ace\\doc\\keyboards\\keychron\\config.txt";
    static String cont = "镓簩缁寸爜浠樻，缁欘?茶姳寮？娣℃颚";

    public static void main(String[] args) throws IOException {
        List<String> ls = getDecodeList();
        File file = new File(path);
        try (InputStream input = new FileInputStream(file)) {
            for (String decode : ls) {
                StringBuffer buffer = new StringBuffer();
                byte[] bytes = new byte[1024];
                for (int n; (n = input.read(bytes)) != -1; ) {
                    buffer.append(new String(bytes, 0, n, decode));
                }
                System.out.println(buffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    private static List<String> getDecodeList() {
        List<String> list = new ArrayList<>();
        list.add("gbk");
        list.add("utf-8");
        list.add("iso-8859-1");
        list.add("utf-16");
        list.add("big5");
        list.add("big5-hkscs");
        return list;
    }

}


