package ca.unknown.bot.use_cases;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        JsonElement jsonElement = JsonParser.parseString(json);
        return jsonElement.getAsJsonObject();
    }
}