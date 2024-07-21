package ca.unknown.bot.app;

import ca.unknown.bot.use_cases.schedule_reminder.ScheduledReminderInteractor;
import ca.unknown.bot.use_cases.utils.EventListener;
import ca.unknown.bot.use_cases.game.GameInteractor;
import ca.unknown.bot.use_cases.recipe.RecipeInteractor;
import ca.unknown.bot.use_cases.timer.TimerInteractor;
import ca.unknown.bot.use_cases.quiz_me.StudyInteractor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
     *
     * @param args Stores Java command-line arguments
     */
    public static void main(String[] args) {
        // Creates an instance of the bot with general permissions.
        // "TOKEN" is an environment variable.
        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"),
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();

        // Adds a simple event listener for testing purposes.
        jda.addEventListener(new EventListener());

        // Adds use-case interactors to the bot instance.
        jda.addEventListener(new GameInteractor());
        jda.addEventListener(new RecipeInteractor(jda));
        jda.addEventListener(new TimerInteractor());
        jda.addEventListener(new ScheduledReminderInteractor());
        StudyInteractor studyInteractor = new StudyInteractor(jda);
        jda.addEventListener(studyInteractor);

        // Adds commands to the bot instance.
        jda.updateCommands().addCommands(
                Commands.slash("rock-paper-scissors", "Starts a game of rock paper scissors.")
                        .addOptions(new OptionData(OptionType.STRING, "choice", "Rock, paper, or scissors.")
                                .addChoice("Rock", "rock")
                                .addChoice("Paper", "paper")
                                .addChoice("Scissors", "scissors")),
                Commands.slash("trivia", "Starts a game of trivia."),
                Commands.slash("timer_create", "Creates a new timer preset")
                        .addOption(OptionType.NUMBER, "work", "how long a work session should be")
                        .addOption(OptionType.NUMBER, "break", "how long a break should be")
                        .addOption(OptionType.INTEGER, "iteration", "how many times you want a cycle to repeat")
                        .addOption(OptionType.STRING, "name", "the name of the timer"),
                Commands.slash("timer_start", "Initiates a Timer")
                        .addOption(OptionType.STRING, "name", "the name of the timer instance"),
                Commands.slash("timer_cancel", "Cancels ongoing timer"),
                Commands.slash("find-recipes", "Suggests recipes based on the name of a food.")
                        .addOption(OptionType.STRING, "food", "Enter the name of a food.", true)
                        .addOption(OptionType.INTEGER, "count", "Enter a positive integer", true)
                        .addOptions(new OptionData(OptionType.STRING, "meal_type", "Choose a type of meal.")
                                .addChoice("Breakfast", "breakfast")
                                .addChoice("Lunch", "lunch")
                                .addChoice("Snack", "snack")
                                .addChoice("Teatime", "teatime")
                                .addChoice("Dinner", "dinner")),
                Commands.slash("schedule_exam", "Schedules a new exam reminder. Please format your date as " +
                                " YYYY MM DD HR MIN SEC.")
                        .addOption(OptionType.STRING, "course", "The course code of the exam.", true)
                        .addOption(OptionType.STRING, "location", "The location of your exam.", true)
                        .addOption(OptionType.INTEGER, "year", "The year of your exam.", true)
                        .addOption(OptionType.INTEGER, "month", "The numeric month of your exam.", true)
                        .addOption(OptionType.INTEGER, "day", "The numeric day of your exam.", true)
                        .addOption(OptionType.INTEGER, "hour", "The military hour of your exam.", true)
                        .addOption(OptionType.INTEGER, "minute", "The minutes value of your exam time.", true)
                        .addOption(OptionType.INTEGER, "sec", "The seconds value of your exam time.", true),
                Commands.slash("schedule_assignment", "Schedules a new assignment reminder. Please " +
                                "format your date as YYYY MM DD HR MIN SEC.")
                        .addOption(OptionType.STRING, "course", "The course code of the assignment.", true)
                        .addOption(OptionType.STRING, "assignment", "The name of your assignment.", true)
                        .addOption(OptionType.INTEGER, "year", "The year of your assignment due date.", true)
                        .addOption(OptionType.INTEGER, "month", "The numeric month of your assignment due date.", true)
                        .addOption(OptionType.INTEGER, "day", "The numeric day of your assignment due date.", true)
                        .addOption(OptionType.INTEGER, "hour", "The military hour of your assignment due date.", true)
                        .addOption(OptionType.INTEGER, "minute", "The minutes value of your assignment due date.", true)
                        .addOption(OptionType.INTEGER, "sec", "The seconds value of your assignment due date.", true),
                Commands.slash("schedule_event", "Schedules a generic event reminder. Please " +
                                "format your date as YYYY MM DD HR MIN SEC.")
                        .addOption(OptionType.STRING, "event", "The name of your event.", true)
                        .addOption(OptionType.INTEGER, "year", "The year of your event.", true)
                        .addOption(OptionType.INTEGER, "month", "The numeric month of your event.", true)
                        .addOption(OptionType.INTEGER, "day", "The numeric day of your event.", true)
                        .addOption(OptionType.INTEGER, "hour", "The military hour of your event.", true)
                        .addOption(OptionType.INTEGER, "minute", "The minutes value of your event.", true)
                        .addOption(OptionType.INTEGER, "sec", "The seconds value of your event.", true),
                Commands.slash("current_schedule", "Displays the user's upcoming event schedule."),
                Commands.slash("clear_schedule", "Clears the user's current schedule."),
                Commands.slash("study-help", "Get Study Help!")
                        .addOptions(new OptionData(OptionType.STRING, "choice",
                                        "How can we help with studying?")
                                .addChoice("Reset Notes", "resetnotes")
                                .addChoice("Add Question", "addquestion")
                                .addChoice("Study", "study")
                                .addChoice("Save Quiz ", "savenotes")
                                .addChoice("Load Quiz", "loadnotes"))

                ).queue();
    }
}
