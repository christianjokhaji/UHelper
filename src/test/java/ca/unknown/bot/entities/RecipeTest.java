package ca.unknown.bot.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    @DisplayName("test labels stored in recipes")
    void getLabel() {
        Recipe recipe = new Recipe("Teriyaki Chicken");
        assertEquals("Teriyaki Chicken", recipe.getLabel());
    }

    @Test
    void getResponse() {
        Recipe recipe = new Recipe("Gravy Tomato");
        assertEquals("Gravy Tomato\n", recipe.getResponse());
    }
}