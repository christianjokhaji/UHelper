package ca.unknown.bot.data_access.templates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
/**
 * This class provides utility methods for generating URL parameters.
 * It helps encode key-value pairs for inclusion in URLs, ensuring that special characters
 * are properly escaped according to the `UTF-8` encoding scheme.
 */

public class URLGenerator {
    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    /**
     * Constructs a URL parameter string from a hashmap of key-value pairs.
     * Each key and value is URL-encoded to ensure proper formatting in a URL query string.
     *
     * @param params a hashmap containing the key-value pairs to be included
     *               in the URL parameter string.
     * @return a URL-encoded string representing the parameters for inclusion in a URL.
     */
    public static String getParamURL(HashMap<String, String> params) {
        StringBuilder url = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            String encodedKey = encodeValue(key);
            String encodedValue = encodeValue(value);
            url.append("&").append(encodedKey).append("=").append(encodedValue);
        }
        return url.toString();
    }
}
