package ca.unknown.bot.app;

import ca.unknown.bot.commands.CommandManager;
import ca.unknown.bot.use_cases.recipe.RecipeInteractor;
import ca.unknown.bot.use_cases.schedule_reminder.ScheduledReminderInteractor;
import ca.unknown.bot.use_cases.utils.EventListener;
import ca.unknown.bot.use_cases.game.GameInteractor;
import ca.unknown.bot.use_cases.timer.TimerInteractor;
import ca.unknown.bot.use_cases.quiz_me.StudyInteractor;
import ca.unknown.bot.use_cases.wiki.WikiInteractor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * This the main class for starting the JDA bot application on cloud.
 */
public class Main {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
     * <p>This method performs the following actions:</p>
     * <ul>
     *     <li>Creates an instance of the bot with general permissions
     *     and necessary gateway intents.</li>
     *     <li>Retrieves the bot's authentication token - an environment variable or the system
     *     properties unless args.length > 0.</li>
     *     <li>Registers various use case interactors that inherits from the event listeners
     *     provided by JDA.</li>
     *     <li>Calls the command manager to add Slash commands to the JDA instance.</li>
     * </ul>
     *
     * @param args Stores Java command-line arguments
     */

    public static void main(String[] args) {
        String token;
        if (args.length == 0) {
            token = System.getenv("TOKEN");
        }
        else {
            token = System.getProperty("TOKEN");
        }

        JDA jda = JDABuilder.createDefault(token,
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();

        jda.addEventListener(new EventListener());
        jda.addEventListener(new GameInteractor());
        jda.addEventListener(new RecipeInteractor(jda));
        jda.addEventListener(new TimerInteractor());
        jda.addEventListener(new ScheduledReminderInteractor());
        jda.addEventListener(new StudyInteractor(jda));
        jda.addEventListener(new WikiInteractor(jda));

        CommandManager.registerCommands(jda);
    }
}
