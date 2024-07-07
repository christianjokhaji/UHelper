package ca.unknown.bot.use_cases;

import ca.unknown.bot.data_access.RecipeApi;
import ca.unknown.bot.entities.Recipe;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class RecipeInteractor extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("find-recipes")) {
            String query = Objects.requireNonNull(event.getOption("ingredient")).getAsString();
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();

            // Fetch recipes from EDAMAM API according to the query
            RecipeApi api = new RecipeApi();
            List<Recipe> recipes = api.fetchRecipes(query);

            // After fetching recipes, now we want to form a response
            StringBuilder response = new StringBuilder("Here are 3 suggested recipes upon " + query + ":\n");
            for (int i = 0; i < recipes.size() && i < 3; i++) {
                Recipe recipe = recipes.get(i);
                response.append((i + 1)).append(". ")
                        .append(recipe.getResponse());
//            Cannot use this reply for reply longer than 3 seconds
//            event.reply(response.toString()).queue();
            event.getHook().sendMessage(response.toString()).queue();
        }
    }
}
}
