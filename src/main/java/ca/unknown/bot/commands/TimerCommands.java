package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class TimerCommands {
    public static SlashCommandData getTimerCreateCommand() {
        return Commands.slash("timer-create", "Creates a new Pomodoro timer.")
                .addOption(OptionType.NUMBER, "work", "how long a work session should be", true)
                .addOption(OptionType.NUMBER, "break", "how long a break should be", true)
                .addOption(OptionType.INTEGER, "iteration",
                        "how many times you want a cycle to repeat", true)
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }

    public static SlashCommandData getTimerDeleteCommand() {
        return Commands.slash("timer-delete", "Delete a Pomodoro timer")
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }

    public static SlashCommandData getTimerListCommand() {
        return Commands.slash("timer-list", "Provides the list of the timers you have.");
    }

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

    public static SlashCommandData getTimerCancelCommand() {
        return Commands.slash("timer-cancel", "Cancels the specified timer if it's running")
                .addOption(OptionType.STRING, "name", "the name of the timer", true);
    }
}