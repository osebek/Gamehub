package cz.ondra.gamehub.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static JsonObject convertToJsonObject(Object object) {
        JsonElement je = gson.toJsonTree(object);
        return (JsonObject) je;
    }

    public static String convertToString(Object object) {
        return gson.toJson(object);
    }

    public static <T> T convertToObject(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> T convertToObject(JsonObject jsonObject, Class<T> clazz) {
        return gson.fromJson(jsonObject, clazz);
    }
}
