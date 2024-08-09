package ca.unknown.bot.data_access.templates;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * A utility class for sending GET requests to an API endpoint.
 * <p>
 * This class provides a method to fetch data from a specified API URL by sending
 * an HTTP GET request and returning the response body as a string. It also handles runtime
 * exception if the process fails.
 * </p>
 */
public class APIFetcher {

    /**
     * Fetches the data from the API.
     *<p> The method constructs an HTTP client that automatically follows redirects,
     * builds an HTTP GET request for the specified URL, and sends the request.
     * The response body is returned as a string.</p>
     * @param url represents the API URL.
     * @return the String representation of the request body (to be parsed into JSON).
     * @throws RuntimeException if the URL is malformed or the request fails due to I/O,
     * interruption, or other exceptions.
     */
    public static String fetch(String url) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}