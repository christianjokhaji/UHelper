package ca.unknown.bot.entities;

/**
 * This is an abstract class for retrieving recipe data
 **/

public class Recipe {
    private final String label;

    public Recipe(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getResponse(){
        return label + "\n";
    }

}
