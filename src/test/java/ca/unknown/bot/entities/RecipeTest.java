package ca.unknown.bot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe recipe;
    private final String imageURL = "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

    @BeforeEach
    void setUp() {
        recipe = new Recipe("Teriyaki Chicken", imageURL);
    }

    @Test
    @DisplayName("test labels stored in recipes")
    void getLabel() {
        assertEquals("Teriyaki Chicken", recipe.getLabel());
    }

    void getImage() {
        assertEquals(imageURL, recipe.getImage());
    }
}