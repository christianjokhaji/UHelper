package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.RockPaperScissors;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ca.unknown.bot.entities.Game;

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
            Game RPS = new RockPaperScissors();
            RPS.startGame(event);
        }
    }
}