package com;

import com.google.gson.internal.LinkedTreeMap;
import com.util.FileUtil;
import com.util.JsonUtil;
import com.util.StringUtil;
import org.apache.hadoop.util.LightWeightGSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.lang.model.element.Element;
import javax.persistence.Table;
import java.io.IOException;
import java.util.*;

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

