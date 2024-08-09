package ca.unknown.bot.entities.recipe;

import java.util.List;

/**
 * Represents a recipe entity that encapsulates the essential details of a recipe,
 * including its label, image, source, and ingredients.
 * <p>
 * This class serves as a model in the application's domain layer, holding the data
 * retrieved from external API (EDAMAM API). It is primarily used for storing and transferring
 * recipe data across different components of the system.
 * </p>
 */
public class Recipe {
    private final String label;
    private final String image;
    private final String source;
    private final String url;
    private final String shareAs;
    private final List<String> ingredientLines;
    /**
     * Constructs a new Recipe with the specified details.
     *
     * @param label           the label of the recipe.
     * @param image           the image URL of the recipe.
     * @param source          the name of the source recipe.
     * @param url             the URL to the source recipe.
     * @param shareAs         the URL to the shareAs link of recipe provided by EDAMAM API.
     * @param ingredientLines the list of ingredient lines for the recipe.
     */
    public Recipe(String label, String image, String source, String url, String shareAs, List<String> ingredientLines) {
        this.label = label;
        this.image = image;
        this.source = source;
        this.url = url;
        this.ingredientLines = ingredientLines;
        this.shareAs = shareAs;
    }
    /**
     * Gets the label of the recipe.
     *
     * @return the label of the recipe.
     */
    public String getLabel() { return label;}
    /**
     * Gets the image URL of the recipe.
     *
     * @return the image URL of the recipe.
     */
    public String getImage() { return image;}
    /**
     * Gets the name of the source recipe.
     *
     * @return the name of source recipe.
     */
    public String getSource() { return source;}
    /**
     * Gets the URL to the source recipe.
     *
     * @return the URL to the source recipe.
     */
    public String getUrl() { return url;}
    /**
     * Gets the URL to the shareAs link of recipe provided by EDAMAM API.
     *
     * @return the URL to the shareAs link of recipe provided by EDAMAM API.
     */
    public String getShareAs() { return shareAs;}
    /**
     * Gets the list of ingredient lines for the recipe.
     *
     * @return the list of ingredient lines for the recipe.
     */
    public String getIngredientLines() {
        StringBuilder ingredients = new StringBuilder();
        for (String ingredient: ingredientLines) {
            ingredients.append("- ").append(ingredient).append('\n');
        }
        return ingredients.toString();
    }
}
