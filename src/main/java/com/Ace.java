package com;

import com.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @Classname: Ace
 * @Date: 2022/8/9 上午 09:42
 * @Author: kalam_au
 * @Description:
 */


public class Ace {
    private static final Logger log = LogManager.getLogger(Ace.class.getName());


    public static void main(String[] args) throws IOException {

        String a = "e9aeacf2-d9d7-457b-acef-e1f177165eca.mp4";
        System.out.println(StringUtil.split(a,".")[0]);
    }


}

