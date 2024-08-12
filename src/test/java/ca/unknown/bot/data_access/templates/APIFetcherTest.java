package ca.unknown.bot.data_access.templates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class APIFetcherTest {

    @Test
    @DisplayName("Test Valid Url")
    void fetch() {
        // Replace with a real or mock URL for a valid test
        String url = "https://eightballapi.com/api/biased?question=what+should+i+eat";
        String result = APIFetcher.fetch(url);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Test Invalid Url")
    void fetchInvalidUrl() {
        String invalidUrl = "http://invalid.url";

        Executable executable = () -> APIFetcher.fetch(invalidUrl);

        // Expect a RuntimeException due to the invalid URL
        Assertions.assertThrows(RuntimeException.class, executable);
    }
}