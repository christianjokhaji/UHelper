package ca.unknown.bot.use_cases.recipe;

import ca.unknown.bot.interface_adapter.recipe.RecipeApiHandler;
import ca.unknown.bot.interface_adapter.recipe.RecipeModel;
import ca.unknown.bot.interface_adapter.templates.Paginator;
import ca.unknown.bot.entities.recipe.Recipe;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A use-case interactor for finding recipes.
 */
public class RecipeInteractor extends ListenerAdapter {
    /**
     * Extract food query, number of recipes, and optional parameters and generate a recipe search.
     *
     * @param event represents a SlashCommandInteraction event.
     * @param jda creates an alias of jda to make the paginator event listener to work
     *
     *  Expected output: send the user a pagination with summary and recipe(s) requested.
     */

    private final JDA jda;

    public RecipeInteractor(JDA jda){
        this.jda = jda;
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("find-recipes")) {
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();

            // Get the query
            String query = Objects.requireNonNull(event.getOption("food")).getAsString();
            int n = Objects.requireNonNull(event.getOption("count")).getAsInt();

            if (n < 0) {
                event.getHook().sendMessage("Please try again with a positive number.").queue();
                return;
            }

            // Generate key-value pairs for every parameter entered
            HashMap<String, String> params = getOptionalParameters(event);

            // Fetch recipes from EDAMAM API according to the query
            List<Recipe> recipes = new RecipeApiHandler(query, n, params).fetchRecipes();
            // After fetching recipes, now we want to form a response
            List<EmbedBuilder> recipeEmbeds = RecipeModel.getResponseEmbeds(
                    query, recipes, n, params
            );
            // initiate the recipe embeds with paginator
            jda.addEventListener(new Paginator(event, recipeEmbeds));
        }
    }

    private HashMap<String, String> getOptionalParameters(SlashCommandInteractionEvent event) {
        HashMap<String, String> params = new HashMap<>();

        // Extract diet-label
        OptionMapping dietLabelOption = event.getOption("diet-label");
        if (dietLabelOption != null) {
            params.put("diet", dietLabelOption.getAsString());
        }


        // Extract meal-type
        OptionMapping mealTypeOption = event.getOption("meal-type");
        if (mealTypeOption != null) {
            params.put("mealType", mealTypeOption.getAsString());
        }

        // Extract dish-type
        OptionMapping dishTypeOption = event.getOption("dish-type");
        if (dishTypeOption != null) {
            params.put("dishType", dishTypeOption.getAsString());
        }

        // Extract cuisine-type
        OptionMapping cuisineTypeOption = event.getOption("cuisine-type");
        if (cuisineTypeOption != null) {
            params.put("cuisineType", cuisineTypeOption.getAsString());
        }

        return params;
    }
}
