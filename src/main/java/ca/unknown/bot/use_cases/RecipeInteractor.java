package ca.unknown.bot.use_cases;

import ca.unknown.bot.interface_interactor.RecipeApiHandler;
import ca.unknown.bot.entities.Recipe;
import ca.unknown.bot.interface_interactor.RecipeModel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecipeInteractor extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("find-recipes")) {
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();
            // Get the query
            String query = Objects.requireNonNull(event.getOption("food")).getAsString();
            int n = Objects.requireNonNull(event.getOption("count")).getAsInt();
            HashMap<String, String> params = new HashMap<>();
            OptionMapping mealTypeOption = event.getOption("meal_type");
            if (mealTypeOption != null) {
                String value = Objects.requireNonNull(event.getOption("meal_type")).getAsString();
                params.put("mealType", value);
            }

            // Generate a summary for the user's inputs
            StringBuilder summary = new StringBuilder();
            summary.append("Here is a summary of your search:\nFood: ").append(query).append("\n");
            summary.append("Number of recipe(s) requested: ").append(n).append("\n");
            if (!params.isEmpty()) {
                for (String key : params.keySet()) {
                    summary.append(key).append(": ").append(params.get(key)).append("\n");
                }
            }
            String summaryString = summary.toString();
            event.getHook().sendMessage(summaryString).queue();

            // Fetch recipes from EDAMAM API according to the query
            List<Recipe> recipes = RecipeApiHandler.fetchRecipes(query, n, params);
            // After fetching recipes, now we want to form a response
            String response = RecipeModel.getResponse(query, recipes, n);
            event.getHook().sendMessage(response).queue();
        }
    }
}
