package com.ace;

import com.util.FileUtil;
import com.util.PathUtil;
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

        String location = "C:\\ideaPorject\\framework_upgrade\\AceDemoWeb";
        FileUtil fileUtil = new FileUtil();
        for (Object obj : fileUtil.getFilesLocation(location)) {
            if (obj.toString().contains("jar")) {
                System.out.println(obj.toString());
            }
        }

        FileUtil.countByType(location, null);

    }
}

