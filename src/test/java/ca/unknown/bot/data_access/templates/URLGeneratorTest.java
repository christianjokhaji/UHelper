package ca.unknown.bot.data_access.templates;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class URLGeneratorTest {

    @Test
    void getParamURL() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Meal Type", "Main course");
        params.put("param2", "fried octopus");
        String paramURL = URLGenerator.getParamURL(params);
        String expected = "&Meal+Type=Main+course&param2=fried+octopus";
        assertEquals(expected, paramURL);
    }
}