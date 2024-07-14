package ca.unknown.bot.interface_interactor;

import ca.unknown.bot.data_access.APIFetcher;
import ca.unknown.bot.data_access.URLGenerator;
import ca.unknown.bot.entities.Recipe;

import ca.unknown.bot.use_cases.Parser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a class for retrieving recipe
 **/

public class RecipeApiHandler {
    public static List<Recipe> fetchRecipes(String query, int n, HashMap<String, String> params) {
        List<Recipe> recipes = new ArrayList<>();
        String app_id = System.getenv("EDAMAM_ID");
        String app_key = System.getenv("EDAMAM_KEY");
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String recipeApi = "https://api.edamam.com/api/recipes/v2?type=public&q=" +
                q + "&app_id=" + app_id + "&app_key=" + app_key;

        if (params != null) {
            String paramURL = URLGenerator.getParamURL(params);
            recipeApi += paramURL;
        }

        String response = APIFetcher.fetch(recipeApi);
        JsonObject parsed = Parser.parse(response);
        // Get the "hits" array from the JSON object
        JsonArray hitsArray = parsed.getAsJsonArray("hits");

        // Iterate over each element in the "hits" array
        for (int i = 0; i < n; i++) {
//            JsonObject hitObject = hitsArray.get(i).getAsJsonObject();
//            JsonObject recipeObject = hitObject.getAsJsonObject("recipe");
            JsonObject recipeObject = hitsArray.get(i).getAsJsonObject().getAsJsonObject("recipe");

            String label = recipeObject.get("label").getAsString();
            String image = recipeObject.get("image").getAsString();
            String source = recipeObject.get("source").getAsString();
            String url = recipeObject.get("url").getAsString();
            String shareAs = recipeObject.get("shareAs").getAsString();
            // parse ingredientLines
            JsonArray ingredients = recipeObject.get("ingredientLines").getAsJsonArray();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> ingredientLines = gson.fromJson(ingredients, listType);
            // TODO: Add more fields as needed

            recipes.add(new Recipe(label, image, source, url, shareAs, ingredientLines));
        }

        return recipes;
    }
}
