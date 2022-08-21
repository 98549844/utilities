package com;

import com.util.FileUtil;
import com.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * @Classname: Ace
 * @Date: 2022/8/9 上午 09:42
 * @Author: kalam_au
 * @Description:
 */


public class Ace {
    private static final Logger log = LogManager.getLogger(Ace.class.getName());


    public static void main(String[] args) throws IOException {
        String q2 = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\input\\keychron_q2.json";

        String content = (String) FileUtil.read(q2).get(FileUtil.ORIGINAL);

        System.out.println(content);
        Object ls = JsonUtil.JsonToObjectList(content);
        System.out.println(ls);

    }


}

