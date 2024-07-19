package ca.unknown.bot.interface_interactor;

import ca.unknown.bot.entities.Recipe;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.commons.collections4.functors.FalsePredicate;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecipeModel {
    private static String getSummary (
            String query, int n, HashMap<String, String> params, List<Recipe> recipes
    ) {
        // Summary Part
        StringBuilder summary = new StringBuilder();
        summary.append("- Food: ").append(query).append("\n");
        summary.append("- Number of recipe(s) requested: ").append(n).append("\n");
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                summary.append("- ").append(key).append(": ").append(params.get(key)).append("\n");
            }
        }
        summary.append("\n");
        int count =  recipes.size();
        // A new section with a line that guide the user to flip through the pagination
        if (recipes.isEmpty()) {
            // no recipes found / invalid search keywords
            String noResults = "I'm sorry, but I couldn't find any recipes matching your search for " + query + ".";
            summary.append(noResults).append("\nPlease try again with another food:eyes:");
            return summary.toString();
        } else if (n > recipes.size()) {
            // not enough recipes found
            summary.append("I couldn't find ").append(n).append(" ").append("recipes matching your search, ");
            if (count == 1){
                summary.append("but here is one that match your search for ");
            } else {
                summary.append("but here are ").append(count).append(" ").append("recipes based on ");
            }
            summary.append(query).append(".");
        } else {
            // enough recipes found
            if (n == 1) {
                summary.append("Here is the suggested recipe based on ").append(query).append(".");
            } else {
                summary.append("Here are ").append(count).append(" suggested recipes based on ")
                        .append(query).append(".");
            }
        }
        summary.append(
                " Please use the buttons below to navigate through recipes :point_down:\n \nBon Appetite:yum:\n \n"
        );

        return summary.toString();
    }

    public static List<EmbedBuilder> getResponseEmbed(
            String query, List<Recipe> recipes, int n, HashMap<String, String> params
    ) {
        List<EmbedBuilder> embeds = new ArrayList<>();
        EmbedBuilder recipeSummary = new EmbedBuilder()
                .setColor(Color.green)
                .addField("Here is a summary of your search:", getSummary(query, n, params, recipes), false)
                .setFooter("Search results provided by Edamam API")
                .setTimestamp(Instant.now());
        embeds.add(recipeSummary);

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
                    .setDescription("**Full recipe on [" + recipeSource + "](" + recipeURL+
                            ")**.\nNote: the source link of the recipe may be outdated.")
                    .addField(":point_right: Ingredients", recipeIngredients, true)
                    .addField(":muscle: Nutrients Info", "Provided by [EDAMAM](" + recipeShareAs + ")", true)
                    .setThumbnail(recipe.getImage()));
        }
        return embeds;
    }
}