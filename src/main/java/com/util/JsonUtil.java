package com.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.util.entity.EmployeeEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil implements JsonSerializer<LocalDateTime> {
    private static final Logger log = LogManager.getLogger(JsonUtil.class);


    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  /*  private String ObjectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }*/

    public static String ObjectToJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonUtil());
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static String ObjectToFormattedJson(List objects) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonUtil());
        Gson gson = gsonBuilder.create();
        return gson.toJson(objects);
    }


    public static <T> List<T> JsonToObjectList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }


    public static Object JsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Object.class);
    }

    public static String getPrettyJson(String json) {
        log.info("Format Json ...");
        //在序列化的时候不忽略null值
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String result = gson.toJson(JsonParser.parseString(json));
        log.info("Complete !");
        return result;
    }


    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return new JsonPrimitive(date.getTime());
    }
}
