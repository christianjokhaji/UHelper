package ca.unknown.bot.entities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
