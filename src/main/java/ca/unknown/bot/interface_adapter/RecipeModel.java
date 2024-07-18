package ca.unknown.bot.interface_adapter;

import ca.unknown.bot.entities.Recipe;

import java.util.List;

public class RecipeModel {
    public static String getResponse(String query, List<Recipe> recipes, int n) {
        int count = n;
        StringBuilder response;

        if (recipes.isEmpty()) {
            return "I'm sorry, but I couldn't find any recipes matching your search for " + query + ".";
        }

        if (n > recipes.size()) {
            count =  recipes.size();
            response = new StringBuilder("I couldn't find " + n).append(" ").append("recipes matching your search, ");
            if (count == 1){
                response.append("but here is one that match your search for ");
            } else {
                response.append("but here are ").append(count).append(" ").append("recipes based on ");
            }
            response.append(query).append(":\n");
        } else {
            if (n == 1) {
                response = new StringBuilder("Here is the suggested recipe based on " + query + ":\n");
            } else {
                response = new StringBuilder("Here are " + count + " suggested recipes based on " + query + ":\n");
            }
        }

        // After fetching recipes, now we want to form a response
        for (int i = 0; i < recipes.size() && i < n; i++) {
            Recipe recipe = recipes.get(i);
            response.append((i + 1)).append(". ")
                    .append(recipe.getResponse());
        };
        return response.toString();

    }
}