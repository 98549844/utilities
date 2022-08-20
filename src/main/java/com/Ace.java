package com;

import com.google.gson.Gson;
import com.util.FileUtil;
import com.util.JsonUtil;
import com.util.VersionUtil;
import com.util.constant.CONSTANT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Classname: Ace
 * @Date: 2022/8/9 上午 09:42
 * @Author: kalam_au
 * @Description:
 */


public class Ace {
    private static final Logger log = LogManager.getLogger(Ace.class.getName());


    public static void main11(String[] args) throws IOException {
        String ap = "src/main/resources/file/input/keychron_q2";

        Gson g = JsonUtil.getInstance();
        Map<String, Object> content = FileUtil.read("src/main/resources/file/input/keychron_q2");
        StringBuilder c = (StringBuilder) content.get(CONSTANT.ORIGINAL);
        System.out.println(c.toString());
        System.out.println();
        //   System.out.println(g.toJson());
    }

    public static void main(String[] args) throws Exception {
        String ap = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\";
//        file.listFiles();


        LinkedList<String> l = FileUtil.getFileNameAndDirectory(ap);
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
    }

}

