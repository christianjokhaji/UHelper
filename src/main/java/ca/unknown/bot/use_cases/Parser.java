package ca.unknown.bot.use_cases;

import com.google.gson.Gson;
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
}