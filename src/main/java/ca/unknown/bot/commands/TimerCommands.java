package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
/**
 * This class stores all the slash commands related to Timer feature.
 */
public class TimerCommands {
    /**
     * This method stores the SlashCommand data for /timer-create.
     */
    public static SlashCommandData getTimerCreateCommand() {
        return Commands.slash("timer-create", "Creates a new Pomodoro timer.")
                .addOption(OptionType.NUMBER, "work", "how long a work session should be", true)
                .addOption(OptionType.NUMBER, "break", "how long a break should be", true)
                .addOption(OptionType.INTEGER, "iteration",
                        "how many times you want a cycle to repeat", true)
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }
    /**
     * This method stores the SlashCommand data for /timer-delete.
     */
    public static SlashCommandData getTimerDeleteCommand() {
        return Commands.slash("timer-delete", "Delete a Pomodoro timer")
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }
    /**
     * This method stores the SlashCommand data for /timer-list.
     */
    public static SlashCommandData getTimerListCommand() {
        return Commands.slash("timer-list", "Provides the list of the timers you have.");
    }
    /**
     * This method stores the SlashCommand data for /timer-start.
     */
    public static SlashCommandData getTimerStartCommand() {
        return Commands.slash("timer-start", "Starts a timer.")
                .addOption(OptionType.STRING, "name",
                        "the name of the timer instance", true)
                .addOption(OptionType.USER, "invitee1",
                        "the first user to share a timer (Optional)", false)
                .addOption(OptionType.USER, "invitee2",
                        "the second user to share a timer (Optional)", false)
                .addOption(OptionType.USER, "invitee3",
                        "the third user to share a timer (Optional)", false);
    }
    /**
     * This method stores the SlashCommand data for /timer-cancel.
     */
    public static SlashCommandData getTimerCancelCommand() {
        return Commands.slash("timer-cancel", "Cancels the specified timer if it's running")
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }
}