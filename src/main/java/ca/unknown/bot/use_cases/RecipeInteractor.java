package ca.unknown.bot.use_cases;

import ca.unknown.bot.interface_interactor.RecipeApiHandler;
import ca.unknown.bot.entities.Recipe;
import ca.unknown.bot.interface_interactor.RecipeModel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
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
            // Fetch recipes from EDAMAM API according to the query
            RecipeApiHandler api = new RecipeApiHandler();
            List<Recipe> recipes = api.fetchRecipes(query, n);

            // After fetching recipes, now we want to form a response
            String response = RecipeModel.getResponse(query, recipes, n);
            event.getHook().sendMessage(response).queue();
        }
    }
}
