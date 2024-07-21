package ca.unknown.bot.entities.recipe;

import java.util.List;

/**
 * This is an abstract class for retrieving recipe data
 **/

public class Recipe {
    private final String label;
    private final String image;
    private final String source;
    private final String url;
    private final String shareAs;
    private final List<String> ingredientLines;

    public Recipe(String label, String image, String source, String url, String shareAs, List<String> ingredientLines) {
        this.label = label;
        this.image = image;
        this.source = source;
        this.url = url;
        this.ingredientLines = ingredientLines;
        this.shareAs = shareAs;
    }

    public String getLabel() { return label;}
    public String getImage() { return image;}
    public String getSource() { return source;}
    public String getUrl() { return url;}
    public String getShareAs() { return shareAs;}
    public String getIngredientLines() {
        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < ingredientLines.size(); i++) {
            ingredients.append((i+1)).append(". ").append(ingredientLines.get(i)).append('\n');
        }
        return ingredients.toString();
    }
}
