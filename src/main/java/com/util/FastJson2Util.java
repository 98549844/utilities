package com.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: FastJsonUtil
 * @Date: 2023/3/22 下午 06:38
 * @Author: kalam_au
 * @Description:
 */


public class FastJson2Util {
    private static final Logger log = LogManager.getLogger(FastJson2Util.class.getName());

    //https://blog.csdn.net/qq_33697094/article/details/128114939

    public static String formatJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return JSON.toJSONString(jsonObject, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNulls);
    }


    public static String ObjectToJson(Object object) {
        String json = JSON.toJSONString(object);
        json = json.replace("\\", "");
        return json;
    }

    public static byte[] ObjectToByteArray(Object object) {
        return ObjectToJson(object).getBytes();
    }

    public static <T> T BytesArrayToObject(byte[] bytes, Class<T> type) {
        return JSON.parseObject(bytes, type);
    }


    public static JSONObject JsonToObject(String json) {
        return JSONObject.parseObject(json);
    }


}

