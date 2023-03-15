package com.ace;

import com.util.FileUtil;
import com.util.PathUtil;
import com.util.TimerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: Ace
 * @Date: 2023/3/8 下午 04:08
 * @Author: kalam_au
 * @Description:
 */


public class Ace {
    private static final Logger log = LogManager.getLogger(Ace.class.getName());


    public static void main(String[] args) throws IOException {
        String p = "C:\\ideaPorject\\ace\\src";
        FileUtil.countByType(p,"java","xml","properties","html","yml");

    }
}

