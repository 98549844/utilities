package com;

import com.google.gson.internal.LinkedTreeMap;
import com.util.FileUtil;
import com.util.JsonUtil;
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
        String a = "51b2a250-b8b0-4879-b45f-fc0d08e6c157.jpg";
        String b = "464f2272-01c3-4d72-9e5d-0e9717d65f6e.jpg";

        List<String> h = new ArrayList<>();
        List<String> h1 = new ArrayList<>();
        h.add(a);
        h.add(b);

        h1 = FileUtil.getNames(h);

        String c = FileUtil.getName(a);
        System.out.println(c);
    }


}

