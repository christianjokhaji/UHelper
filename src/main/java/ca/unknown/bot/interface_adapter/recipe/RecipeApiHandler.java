package ca.unknown.bot.interface_adapter.recipe;

import ca.unknown.bot.data_access.templates.APIFetcher;
import ca.unknown.bot.data_access.templates.URLGenerator;
import ca.unknown.bot.entities.recipe.Recipe;

import ca.unknown.bot.data_access.templates.Parser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This is a class for retrieving recipe
 **/

public class RecipeApiHandler {
    private final int n;
    private final String recipeApi;
    private List<Recipe> recipes;

    // initialize the RecipeApiHandler
    public RecipeApiHandler(String query, int n, HashMap<String, String> params){
        this.n = n;
        this.recipes = new ArrayList<>();
        String app_id = System.getenv("EDAMAM_ID");
        String app_key = System.getenv("EDAMAM_KEY");
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String recipeApi = "https://api.edamam.com/api/recipes/v2?type=public&q=" +
                q + "&app_id=" + app_id + "&app_key=" + app_key;

        if (params != null) {
            String paramURL = URLGenerator.getParamURL(params);
            recipeApi += paramURL;
        }
        this.recipeApi = recipeApi;
    }

    private JsonObject fetchedAndParsed(String URL){
        String response = APIFetcher.fetch(URL);
        return Parser.parse(response);
    }

    private JsonArray getHitsArray(String URL){
        JsonObject ApiResult = fetchedAndParsed(URL);
        // Get the "hits" array from the JSON object
        JsonArray hitsArray = ApiResult.get("hits").getAsJsonArray();
        // Get the number of the recipes available
        int count = ApiResult.get("count").getAsInt() - 20;
        // Get the number of recipes we have yet searched but requested by the user (i.e., n > 20)
        int curr = n - 20;
        while (count > 0 && curr > 0) {
            String nextURL = ApiResult.getAsJsonObject("_links")
                    .getAsJsonObject("next").get("href").getAsString();
            ApiResult = fetchedAndParsed(nextURL);
            JsonArray nextHitsArray = ApiResult.getAsJsonArray("hits");
            hitsArray.addAll(nextHitsArray);
            curr -= 20;
            count -= 20;
        }
        return hitsArray;
    }

    public List<Recipe> fetchRecipes() {
        JsonArray allResults = getHitsArray(recipeApi);

        // Iterate over each element in the "hits" array
        for (int i = 0; i < n && i < allResults.size(); i++) {
            JsonObject recipeObject = allResults.get(i).getAsJsonObject().getAsJsonObject("recipe");

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

        Collections.shuffle(recipes);

        return recipes;
    }
}
