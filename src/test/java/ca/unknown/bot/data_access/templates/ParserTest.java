package ca.unknown.bot.data_access.templates;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        String example = "{\"Label\": \"Teriyaki Chicken\", \"Instructions\":[{1: \"a\", " +
                "2:\"b\"}]}";

        assertTrue(Parser.parse(example).isJsonObject());
    }
}