package ca.unknown.bot.interface_interactor;

import ca.unknown.bot.entities.Recipe;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

        return response.toString();

    }

    private static String getSummary (
            String query, int n, HashMap<String, String> params
    ) {
        StringBuilder summary = new StringBuilder();
        summary.append("- Food: ").append(query).append("\n");
        summary.append("- Number of recipe(s) requested: ").append(n).append("\n");
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                summary.append("- ").append(key).append(": ").append(params.get(key)).append("\n");
            }
        }
        return summary.toString();
    }

    public static List<EmbedBuilder> getResponseEmbed(
            String query, List<Recipe> recipes, int n, HashMap<String, String> params
    ) {
        List<EmbedBuilder> embeds = new ArrayList<>();

        for (int i = 0; i < recipes.size() && i < n; i++) {
            Recipe recipe = recipes.get(i);
            String recipeLabel = (i + 1) + ". " +
                    recipe.getLabel();
            String recipeSource = recipe.getSource();
            String recipeURL = recipe.getUrl();
            String recipeShareAs = recipe.getShareAs();
            String recipeIngredients = recipe.getIngredientLines();
            // might want to add pagination to iterate embeds
            embeds.add(new EmbedBuilder()
                    .setColor(Color.orange)
                    .setTitle(recipeLabel)
                    .setDescription("**Full recipe on [" + recipeSource + "](" + recipe.getUrl()+
                            ")**.\nNote: the link to the recipe may be outdated.")
                    .addField("Ingredients", recipeIngredients, true)
                    .addField("Nutrients Info", "Provided by [EDAMAM](" + recipeShareAs + ")", true)
                    .setThumbnail(recipe.getImage()));
        };

        EmbedBuilder recipeSummary = new EmbedBuilder()
                .setColor(Color.green)
                .addField("Here is a summary of your search:", getSummary(query, n, params), false)
                .setFooter("Search results provided by Edamam API")
                .setTimestamp(Instant.now());

        embeds.add(recipeSummary);
        return embeds;
    }
}