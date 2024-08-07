package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class WikiCommands {
    public static SlashCommandData getWikiCommands(){
//        return Commands.slash("uhelper-wiki", "A quick guide to UHelper's commands.")
//                .addOptions(new OptionData(OptionType.STRING, "feature",
//                        "Which feature do u want to explore?", true)
//                        .addChoice("General", "general")
//                        .addChoice("Scheduled Reminders", "scheduled_reminders")
//                        .addChoice("Timer", "timer")
//                        .addChoice("Study Helper", "study_helper")
//                        .addChoice("Mini Games", "mini_games")
//                        .addChoice("Find Recipes", "find_recipes"));
        return Commands.slash("uhelper-wiki", "A quick guide to UHelper's commands.")
                .addOptions(new OptionData(OptionType.STRING, "feature",
                        "Which feature do u want to explore?", true)
                        .addChoice("General", "general")
                        .addChoice("Scheduled Reminders", "scheduled_reminders")
                        .addChoice("Timer", "timer")
                        .addChoice("Study Helper", "study_helper")
                        .addChoice("Mini Games", "mini_games")
                        .addChoice("Find Recipe", "find_recipe")
                );
    }
}
