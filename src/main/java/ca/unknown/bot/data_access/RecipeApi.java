package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RecipeApi {

/**
 * This is a class for retrieving recipe
 **/

    public List<Recipe> fetchRecipes(String q) {
        List<Recipe> recipes;
        try {
            recipes = new ArrayList<>();
            String app_id = System.getenv("EDAMAM_ID");
            String app_key = System.getenv("EDAMAM_KEY");
            String apiURL = String.format(
                    "https://api.edamam.com/api/recipes/v2?type=public&q=%s&app_id=%s&app_key=%s", q, app_id, app_key
            );

            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if the connect is made
            int responseCode = conn.getResponseCode();

            // 200 means OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode:" + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                // Close the scanner
                scanner.close();

                // Parse the response with org.json
                String jsonString = informationString.toString();
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonString));

                // Get the "hits" array from the JSON object
                JSONArray hitsArray = jsonObject.getJSONArray("hits");

                // Iterate over each element in the "hits" array
                for (int i = 0; i < 3; i++) {
                    JSONObject hitObject = hitsArray.getJSONObject(i);
                    JSONObject recipeObject = hitObject.getJSONObject("recipe");

                    String label = recipeObject.getString("label");
                    // TODO: Add more fields as needed

                    recipes.add(new Recipe(label));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return recipes;
    }
}
