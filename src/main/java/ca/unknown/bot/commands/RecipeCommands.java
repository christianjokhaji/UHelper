package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
/**
 * This class stores all the slash commands related to Recipe feature.
 */
public class RecipeCommands {
    /**
     * This method stores the SlashCommand data for /find-recipes.
     * There are two options - food and count - set to required, while rest of the options
     * (diet-label, meal-type, dish-type, cuisine-type) are set to optional.
     */
    public static SlashCommandData getFindRecipesCommand() {
        return Commands.slash("find-recipes", "Suggests recipes based on the name of a food.")
                .addOption(OptionType.STRING, "food",
                        "Name one food on your mind.", true)
                .addOption(OptionType.INTEGER, "count",
                        "How many recipes do you want?", true)
                .addOptions(new OptionData(OptionType.STRING, "diet-label",
                        "(Optional) Choose a type of diet.", false)
                        .addChoice("Balanced", "balanced")
                        .addChoice("High-Fiber", "high-fiber")
                        .addChoice("High-Protein", "high-protein")
                        .addChoice("Low-Carb", "low-carb")
                        .addChoice("Low-Fat", "low-fat")
                        .addChoice("Low-Sodium", "low-sodium"))
                .addOptions(new OptionData(OptionType.STRING, "meal-type",
                        "(Optional) Choose a type of meal.", false)
                        .addChoice("Breakfast", "breakfast")
                        .addChoice("Lunch", "lunch")
                        .addChoice("Snack", "snack")
                        .addChoice("Teatime", "teatime")
                        .addChoice("Dinner", "dinner"))
                .addOptions(new OptionData(OptionType.STRING, "dish-type",
                        "(Optional) Specify the type of dish you are looking for.", false)
                        .addChoice("Biscuits and cookies", "biscuits and cookies")
                        .addChoice("Bread", "bread")
                        .addChoice("Cereals", "cereals")
                        .addChoice("Desserts", "desserts")
                        .addChoice("Drinks", "drinks")
                        .addChoice("Ice cream and Custard", "ice cream and custard")
                        .addChoice("Main course", "main course")
                        .addChoice("Pasta", "pasta")
                        .addChoice("Pizza", "pizza")
                        .addChoice("Pancake", "pancake")
                        .addChoice("Salad", "salad")
                        .addChoice("Sandwich", "sandwiches")
                        .addChoice("Seafood", "Seafood")
                        .addChoice("Side dish", "Side dish")
                        .addChoice("Soup", "soup")
                        .addChoice("Starter", "starter")
                        .addChoice("Sweets", "sweets"))
                .addOptions(new OptionData(OptionType.STRING, "cuisine-type",
                        "(Optional) Choose a type of cuisine.", false)
                        .addChoice("American", "american")
                        .addChoice("Asian", "asian")
                        .addChoice("British", "british")
                        .addChoice("Caribbean", "caribbean")
                        .addChoice("Central Europe", "central europe")
                        .addChoice("Chinese", "chinese")
                        .addChoice("Eastern Europe", "eastern europe")
                        .addChoice("French", "french")
                        .addChoice("Greek", "greek")
                        .addChoice("Indian", "indian")
                        .addChoice("Italian", "italian")
                        .addChoice("Japanese", "japanese")
                        .addChoice("Korean", "korean")
                        .addChoice("Kosher", "kosher")
                        .addChoice("Mediterranean", "mediterranean")
                        .addChoice("Mexican", "mexican")
                        .addChoice("Middle Eastern", "middle eastern")
                        .addChoice("Nordic", "nordic")
                        .addChoice("South American", "south american")
                        .addChoice("South East Asian", "south east asian")
                        .addChoice("World", "world")
                );
    }
}
