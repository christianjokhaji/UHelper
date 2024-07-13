package ca.unknown.bot.data_access;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class URLGenerator {

    // Method to encode a string value using `UTF-8` encoding scheme
    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String getParamURL(HashMap<String, String> params) {
        StringBuilder url = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            String encodedValue = encodeValue(value);
            url.append("&").append(key).append("=").append(encodedValue);
        }
        return url.toString();
    }
}
