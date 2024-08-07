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

public class Main {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
     *
     * @param args Stores Java command-line arguments
     */

    public static void main(String[] args) {
        // Creates an instance of the bot with general permissions.
        // "TOKEN" is an environment variable unless args.length > 0.
        String token;
        if (args.length == 0) {
            token = System.getenv("TOKEN");
        }
        else {
            token = System.getProperty("TOKEN");
        }

        JDA jda = JDABuilder.createDefault(token,
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();

        // Adds a simple event listener for testing purposes.
        jda.addEventListener(new EventListener());

        // Adds use-case interactors to the bot instance.
        jda.addEventListener(new GameInteractor());
        jda.addEventListener(new RecipeInteractor(jda));
        jda.addEventListener(new TimerInteractor());
        jda.addEventListener(new ScheduledReminderInteractor());
        jda.addEventListener(new StudyInteractor(jda));
        jda.addEventListener(new WikiInteractor(jda));

        // Adds commands to the bot instance.
        CommandManager.registerCommands(jda);
    }
}
