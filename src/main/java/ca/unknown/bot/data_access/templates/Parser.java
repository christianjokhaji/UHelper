package ca.unknown.bot.data_access.templates;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A use-case interactor for parsing JSON strings.
 */
public class Parser {
    /**
     *
     * @param json represents a JSON string.
     * @return a JsonObject for further data manipulation and/or access.
     */
    public static JsonObject parse(String json) {
        return new Gson().fromJson(json, JsonObject.class);
    }
    /**
     *
     * @param json represents a JSON string.
     * @return a JsonArray for further data manipulation and/or access.
     */
    public static JsonArray parseArray(String json) {
        return new Gson().fromJson(json, JsonArray.class);
    }
}
