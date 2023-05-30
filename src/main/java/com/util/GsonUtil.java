package com.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class GsonUtil implements JsonSerializer<LocalDateTime> {
    private static final Logger log = LogManager.getLogger(GsonUtil.class);

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String ObjectToJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonUtil());
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static String ObjectToFormattedJson(List objects) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonUtil());
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
        //在序列化的时候不忽略null值
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(JsonParser.parseString(json));
    }


    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return new JsonPrimitive(date.getTime());
    }
}
