package ca.unknown.bot.use_cases.game;

import ca.unknown.bot.data_access.templates.APIFetcher;
import ca.unknown.bot.entities.game.RockPaperScissors;
import ca.unknown.bot.data_access.templates.Parser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ca.unknown.bot.entities.game.Game;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Map;
import java.util.Objects;

/**
 * A use-case interactor for starting a game.
 */
public class GameInteractor extends ListenerAdapter {

    /**
     * Starts the appropriate game based on the event name.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("rock-paper-scissors")) {
            String choice = Objects.requireNonNull(event.getOption("choice")).getAsString();
            Game RPS = new RockPaperScissors();
            String result = RPS.startGame(choice);
            event.reply(result).queue();
        }
        else if (event.getName().equals("trivia")) {
            String response = APIFetcher.fetch("https://opentdb.com/api.php?amount=1&type=boolean");
            Map<String, JsonElement> parsed = Parser.parse(response).asMap();
            JsonArray results = (JsonArray) parsed.get("results");
            JsonObject dict = (JsonObject) results.get(0);
            String question = StringEscapeUtils.unescapeHtml4(dict.get("question").getAsString());
            String answer = StringEscapeUtils.unescapeHtml4(dict.get("correct_answer").getAsString());
            JDA jda = event.getJDA();
            TriviaListener trivia = new TriviaListener(answer);
            jda.addEventListener(trivia);
            event.reply(question).queue();
        }
        else if (event.getName().equals("8ball")) {
            String question = Objects.requireNonNull(event.getOption("question"))
                    .getAsString().replace(" ", "+");
            String response = APIFetcher.fetch("https://eightballapi.com/api/biased?question="
                    + question + "&lucky=false");
            Map<String, JsonElement> parsed = Parser.parse(response).asMap();
            String result = parsed.get("reading").getAsString();
            event.reply(result).queue();
        }
    }
}
