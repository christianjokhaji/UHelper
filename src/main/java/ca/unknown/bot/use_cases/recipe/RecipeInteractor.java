package ca.unknown.bot.use_cases.recipe;

import ca.unknown.bot.interface_adapter.recipe.RecipeApiController;
import ca.unknown.bot.interface_adapter.recipe.RecipePresenter;
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
 * This is a use case interactor for handling the "find-recipes" command.
 * <p>
 * This interactor manages the process of fetching recipes based on user input,
 * interacting with the Recipe API, and preparing the data for presentation.
 * </p>
 */
public class RecipeInteractor extends ListenerAdapter {


    private final JDA jda;
    /**
     * Constructs a new RecipeInteractor with the specified JDA instance.
     *
     * @param jda The JDA instance, used for managing Discord events and interactions.
     */
    public RecipeInteractor(JDA jda){
        this.jda = jda;
    }
    /**
     * Handles slash command interactions for the "find-recipes" command.
     * Delegates the processing to the execute method.
     *
     * @param event The slash command interaction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("find-recipes")) {
            execute(event);
        }
    }
    /**
     * Executes the recipe-finding use case, handling the extraction of user input,
     * fetching recipes, and preparing the response.
     *
     * @param event The slash command interaction event containing the user's input.
     */
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        String query = Objects.requireNonNull(event.getOption("food")).getAsString();
        int count = Objects.requireNonNull(event.getOption("count")).getAsInt();

        if (count < 0) {
            event.getHook().sendMessage("Please try again with a positive number.").queue();
            return;
        }

        HashMap<String, String> params = getOptionalParameters(event);

        List<Recipe> recipes = new RecipeApiController(query, count, params).fetchRecipes();

        List<EmbedBuilder> recipeEmbeds = RecipePresenter.getResponseEmbeds(
                query, recipes, count, params
        );

        jda.addEventListener(new Paginator(event, recipeEmbeds));
    }

    private HashMap<String, String> getOptionalParameters(SlashCommandInteractionEvent event) {
        HashMap<String, String> params = new HashMap<>();

        OptionMapping dietLabelOption = event.getOption("diet-label");
        if (dietLabelOption != null) {
            params.put("diet", dietLabelOption.getAsString());
        }

        OptionMapping mealTypeOption = event.getOption("meal-type");
        if (mealTypeOption != null) {
            params.put("mealType", mealTypeOption.getAsString());
        }

        OptionMapping dishTypeOption = event.getOption("dish-type");
        if (dishTypeOption != null) {
            params.put("dishType", dishTypeOption.getAsString());
        }

        OptionMapping cuisineTypeOption = event.getOption("cuisine-type");
        if (cuisineTypeOption != null) {
            params.put("cuisineType", cuisineTypeOption.getAsString());
        }

        return params;
    }
}
