package ca.unknown.bot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe("Teriyaki Chicken");
    }

    @Test
    @DisplayName("test labels stored in recipes")
    void getLabel() {
        assertEquals("Teriyaki Chicken", recipe.getLabel());
    }

    @Test
    void getResponse() {
        assertEquals("Teriyaki Chicken\n", recipe.getResponse());
    }
}