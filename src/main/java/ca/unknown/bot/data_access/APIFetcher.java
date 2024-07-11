package ca.unknown.bot.data_access;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A class for sending a GET request to an API endpoint.
 */
public class APIFetcher {

    /**
     * Fetches the data from the API.
     *
     * @param url represents the API URL.
     * @return the String representation of the request body (to be parsed into JSON).
     */
    public static String fetch(String url) {
        try {
            // Build the HTTP client.
            HttpClient client = HttpClient.newHttpClient();
            // Build the HTTP request.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            // Send the request through the HTTP client and return it.
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}