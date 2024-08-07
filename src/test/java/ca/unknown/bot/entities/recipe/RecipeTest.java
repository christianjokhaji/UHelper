package ca.unknown.bot.entities.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        String label = "chicken";
        String image = "imageURL";
        String source = "source";
        String url = "sourceURL";
        String shareAs = "shareAsURL";
        List<String> ingredientLines = new ArrayList<>(Arrays.asList("a", "b", "c"));
        this.recipe = new Recipe(label, image, source, url, shareAs, ingredientLines);
    }

    @Test
    void getLabel() {
        assertEquals("chicken", recipe.getLabel());
    }

    @Test
    void getImage() {
        assertEquals("imageURL", recipe.getImage());
    }

    @Test
    void getSource() {
        assertEquals("source", recipe.getSource());
    }

    @Test
    void getUrl() {
        assertEquals("sourceURL", recipe.getUrl());
    }

    @Test
    void getShareAs() {
        assertEquals("shareAsURL", recipe.getShareAs());
    }

    @Test
    void getIngredientLines() {
        String result = "- a\n- b\n- c\n";
        assertEquals(result, recipe.getIngredientLines());
    }
}