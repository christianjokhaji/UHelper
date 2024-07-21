package ca.unknown.bot.use_cases.recipe;

import ca.unknown.bot.interface_interactor.recipe.RecipeApiHandler;
import ca.unknown.bot.interface_interactor.recipe.RecipeModel;
import ca.unknown.bot.interface_interactor.templates.Paginator;
import ca.unknown.bot.entities.recipe.Recipe;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RecipeInteractor extends ListenerAdapter {

    private final JDA jda;

    public RecipeInteractor(JDA jda){
        // This allows the event listener to work
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
            HashMap<String, String> params = new HashMap<>();
            OptionMapping mealTypeOption = event.getOption("meal_type");
            if (mealTypeOption != null) {
                String value = Objects.requireNonNull(event.getOption("meal_type")).getAsString();
                params.put("Meal Type", value);
            }
            // Fetch recipes from EDAMAM API according to the query
            List<Recipe> recipes = new RecipeApiHandler(query, n, params).fetchRecipes();
            // After fetching recipes, now we want to form a response
            List<EmbedBuilder> recipeEmbeds = RecipeModel.getResponseEmbed(query, recipes, n, params);
            // initiate the recipe embeds with paginator
            jda.addEventListener(new Paginator(event, recipeEmbeds));
        }
    }
}
