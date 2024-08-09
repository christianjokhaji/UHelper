package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
/**
 * This class stores all the slash commands related to Scheduled Reminders feature.
 */
public class ScheduledRemindersCommands {
    /**
     * This method stores the SlashCommand data for /schedule-exam.
     */
    public static SlashCommandData getScheduleExamCommand(){
        return Commands.slash("schedule-exam",
                        "Schedules a new exam reminder. Please format your date as " +
                                "YYYY MM DD HR MIN.")
                .addOption(OptionType.STRING, "course",
                        "The course code of the exam.", true)
                .addOption(OptionType.STRING, "location",
                        "The location of your exam.", true)
                .addOption(OptionType.INTEGER, "year",
                        "The year of your exam.", true)
                .addOption(OptionType.INTEGER, "month",
                        "The numeric month of your exam.", true)
                .addOption(OptionType.INTEGER, "day",
                        "The numeric day of your exam.", true)
                .addOption(OptionType.INTEGER, "hour",
                        "The military hour of your exam.", true)
                .addOption(OptionType.INTEGER, "minute",
                        "The minutes value of your exam time.", true);
    }
    /**
     * This method stores the SlashCommand data for /schedule-assignment.
     */
    public static SlashCommandData getScheduleAssignmentCommand(){
        return Commands.slash("schedule-assignment",
                        "Schedules a new assignment reminder. " +
                                "Please format your date as YYYY MM DD HR MIN.")
                .addOption(OptionType.STRING, "course",
                        "The course code of the assignment.", true)
                .addOption(OptionType.STRING, "assignment",
                        "The name of your assignment.", true)
                .addOption(OptionType.INTEGER, "year",
                        "The year of your assignment due date.", true)
                .addOption(OptionType.INTEGER, "month",
                        "The numeric month of your assignment due date.", true)
                .addOption(OptionType.INTEGER, "day",
                        "The numeric day of your assignment due date.", true)
                .addOption(OptionType.INTEGER, "hour",
                        "The military hour of your assignment due date.", true)
                .addOption(OptionType.INTEGER, "minute",
                        "The minutes value of your assignment due date.", true);
    }
    /**
     * This method stores the SlashCommand data for /schedule-event.
     */
    public static SlashCommandData getScheduleEventCommand(){
        return Commands.slash("schedule-event",
                        "Schedules a generic event reminder. " +
                                "Please format your date as YYYY MM DD HR MIN.")
                .addOption(OptionType.STRING, "event", "The name of your event.", true)
                .addOption(OptionType.INTEGER, "year", "The year of your event.", true)
                .addOption(OptionType.INTEGER, "month", "The numeric month of your event.", true)
                .addOption(OptionType.INTEGER, "day", "The numeric day of your event.", true)
                .addOption(OptionType.INTEGER, "hour", "The military hour of your event.", true)
                .addOption(OptionType.INTEGER, "minute", "The minutes value of your event.", true);
    }
    /**
     * This method stores the SlashCommand data for /current-schedule.
     */
    public static SlashCommandData getCurrentScheduleCommand(){
        return Commands.slash("current-schedule", "Displays the user's upcoming event schedule.");
    }
    /**
     * This method stores the SlashCommand data for /clear-event.
     */
    public static SlashCommandData getClearEventCommand(){
        return  Commands.slash("clear-event", "Clears an event from the user's current schedule.");
    }
    /**
     * This method stores the SlashCommand data for /clear-schedule.
     */
    public static SlashCommandData getClearScheduleCommand(){
        return Commands.slash("clear-schedule", "Clears the user's current schedule.");
    }

}
