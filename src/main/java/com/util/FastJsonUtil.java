package com.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: FastJsonUtil
 * @Date: 2023/3/22 下午 06:38
 * @Author: kalam_au
 * @Description:
 */


public class FastJsonUtil {
    private static final Logger log = LogManager.getLogger(FastJsonUtil.class.getName());

    //https://blog.csdn.net/qq_33697094/article/details/128114939



    public static String ObjectToJson(Object object) {
        return JSON.toJSONString(object);
    }

    public static JSONObject JsonToObject(String json) {
        return JSONObject.parseObject(json);
    }


}
