package ca.unknown.bot.interface_adapter.templates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StylingStringsTest {

    @Test
    void startWithUpperCase() {
        String expect1 = "Hello World";
        String output1 = StylingStrings.startWithUpperCase("hello world");
        assertEquals(expect1, output1);

        String expect2 = "Hello";
        String output2 = StylingStrings.startWithUpperCase("hello");
        assertEquals(expect2, output2);

        String expect3 = "Hello World";
        String output3 = StylingStrings.startWithUpperCase("helloWorld");
        assertEquals(expect3, output3);
    }
}