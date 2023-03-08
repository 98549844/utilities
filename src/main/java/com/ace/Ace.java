package com.ace;

import com.util.FileUtil;
import com.util.TimerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @Classname: Ace
 * @Date: 2023/3/8 下午 04:08
 * @Author: kalam_au
 * @Description:
 */


public class Ace {
    private static final Logger log = LogManager.getLogger(Ace.class.getName());


    public static void main(String[] args) throws IOException {

        TimerUtil timer = new TimerUtil();
        log.info("counting start ...");
        long start = timer.start();
        String location = "C:\\ibmProjects\\";
        // String ext = null;
        FileUtil.countByType(location, null);
        timer.calc(start);
        log.info("counting complete !!!");

    }
}

