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
 * This class is responsible for fetching recipe data from an API.
 * It processes and parses JSON responses to a list of Recipe class instances.
 */

public class RecipeApiController {
    private final int n;
    private final String recipeApi;
    private List<Recipe> recipes;

    /**
     * Constructs a RecipeApiController with the specified query parameters.
     *
     * @param query  the search query for the recipes.
     * @param n      the number of recipes to fetch.
     * @param params additional parameters for the API request.
     */
    public RecipeApiController(String query, int n, HashMap<String, String> params){
        this.n = n;
        this.recipes = new ArrayList<>();
        String app_id;
        String app_key;

        if (System.getenv("EDAMAM_ID") == null || System.getenv("EDAMAM_KEY") == null) {
            app_id = System.getProperty("EDAMAM_ID");
            app_key = System.getProperty("EDAMAM_KEY");
        }
        else {
            app_id = System.getenv("EDAMAM_ID");
            app_key = System.getenv("EDAMAM_KEY");
        }

        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String recipeApi = "https://api.edamam.com/api/recipes/v2?type=public&q=" +
                q + "&app_id=" + app_id + "&app_key=" + app_key;

        if (params != null) {
            String paramURL = URLGenerator.getParamURL(params);
            recipeApi += paramURL;
        }
        this.recipeApi = recipeApi;
    }
    /**
     * Each recipe is extracted from the "hits" array in the JSON
     * response and converted to a Recipe object.
     *
     * @return a list of Recipe objects containing the fetched recipes.
     */
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

            JsonArray ingredients = recipeObject.get("ingredientLines").getAsJsonArray();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> ingredientLines = gson.fromJson(ingredients, listType);

            recipes.add(new Recipe(label, image, source, url, shareAs, ingredientLines));
        }

        Collections.shuffle(recipes);

        return recipes;
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


}
