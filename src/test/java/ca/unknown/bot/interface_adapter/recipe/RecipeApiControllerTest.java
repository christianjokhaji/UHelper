package ca.unknown.bot.interface_adapter.recipe;

import ca.unknown.bot.entities.recipe.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeApiControllerTest {

    @Test
    void fetchRecipes() {
        List<Recipe> expectedRecipes = new ArrayList<>();
        List<String> ingredients = new ArrayList<>(Arrays.asList("1 package (3 ounces) beef " +
                        "ramen noodles",
                "4 hot dogs",
                "5 drops liquid green food coloring, optional",
                "Prepared mustard"));
        Recipe recipe = new Recipe(
                "Octopus and Seaweed Recipe 4",
                "https://edamam-product-images.s3.amazonaws.com/web-img/304/304c1a869d266f2b0773a9283aed9d41.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEIr%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQCkFd4Ih8GtHudrm923C4jD3yS5007ud9ixbOY%2FtgY95QIgDSZd7TY8DYJpwNz3B%2FXOYonRSW3Nw2UW8g%2BH3vGUqU0quQUIYxAAGgwxODcwMTcxNTA5ODYiDEFzZyCk1R4vowEUSyqWBYsHTIqOZEttVFpZQqNGRDAPLjy28KZqUDwQjYAg42KdGSF7qlE1MzJjCnq4caD8AXOpGUyNMr3kPg%2Fe29YzgUwFXRYHMgpN8qqGJtp%2BjvP5oT1S8al0WrtA4dtory%2BeMHnuAs3S%2Be8bJ3P2tVJ%2FUqRXH%2Bxtu%2Bv%2ByMoais9qzxvx954UdN1HUuXV9mdXqAZZe9MM4ac1CI700q2Sl5NLbBtOsh32UJRVHsMGy%2FnBNWN%2FBrVmv%2FTRJTqzyZBkS9IwJ17%2BxHbkaoWra1MiwXAZeeg6kuUoHxdH19Hto20WR1A76wZE%2FjRmTeeeZxI%2BYoQ0GZ8QqPIz2vrtmtqjphIKgbWlloV36n45qU2zJRYi9t0NJMMv0tXtNvFlwrNAD3Odxq2IGy7K9%2BX8oGClG%2BtZayitiSp1VdNR7lMOUYpJfTJ79eac1kfQc9Oea7tFGzf8ufZx6M%2FI0vqTAZieUwSUSSzVfG3Rn3xxaMMfQ6xgLW0Cxy7rdGsBWfgxzru52l0nbTC%2Ba29%2BBNYAN%2B8WrQw%2BOViEexCtqnVFCtEcA42JrPIiT4QNcyLaZ%2Bk86yWrEq0ge0QLOA2splDgPhSFEKPwiYoPF%2FltJEx5op7oZz6YTvOsLWpV0IQIF3AJ5jcBJglI%2BExkCbbUS5qSnrpLRh3AVjMZU5O2PuZO1dvIK%2B6XGlKoWx9v22lOx0tIskgfRe5TFYlMtumMuYUuqgVcZXn1Bq8wUB2YD0XQqS42mWbBweVh%2F0MTUnAbtgJVDdAMaNUv08%2FrrlNFCLYJcF4NKCaBWXlNOZodSH0J1BUvpepNhwtjtIm87xgSHyy%2FqWhmlqZ76ChJATNFGeFsu6uJeqH9XpS6RNSHm6qUi5cPsfm6rJA0TRtkVJJRMPaa9bQGOrEB%2Fbf03onjnOV9%2F1o11rb5vvvpq3vhjEo1700iNhP43B6y94I6yVuEawkw%2BamsPd4jwE1%2B6dzmXmdZxA%2FSqDo7i4mPsZ1UC9%2BD%2FWnEpltluVLMuOtsym%2FmmEb9h7PMxdNYlkY3dh7VC9kXmcgUMMcwnMzhAhZPxkxszzgSyJDGMgzaggQJbC5m7m5DSckc1zQQk310%2Fo%2BzVGQ6atLMYpNoV9cdYIoDq2YbM8npw2uakLjl&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240721T193408Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=ASIASXCYXIIFFZQ45AZG%2F20240721%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=cc630c1f0af9d2aafbd6bddf111a90d4e36e833f36c2b8e96e2ff4195b901281",
                "Taste of Home",
                "http://www.tasteofhome.com/Recipes/Octopus-and-Seaweed",
                "http://www.edamam.com/recipe/octopus-and-seaweed-recipe-4-f150a1c2e24268be51eeb08a856b1e87/octopus+ramen",
                ingredients
                );
        expectedRecipes.add(recipe);
        HashMap<String, String> params = new HashMap<>();
        params.put("Meal Type", "Dinner");
        RecipeApiController testRecipeApiController = new RecipeApiController(
                "octopus ramen",
                1,
                params
        );

        List<Recipe> actualRecipes = testRecipeApiController.fetchRecipes();

        assertEquals(expectedRecipes.size(), actualRecipes.size());
        assertEquals(expectedRecipes.get(0).getLabel(), actualRecipes.get(0).getLabel());

    }
}
