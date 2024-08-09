package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
/**
 * This class stores all the slash commands related to Study Help feature.
 */
public class StudyHelpCommands {
    /**
     * This method stores the SlashCommand data for /study-help.
     */
    public static SlashCommandData getStudyHelpCommand() {
        return Commands.slash("study-help", "Get Study Help!")
                .addOptions(new OptionData(OptionType.STRING, "choice",
                        "How can we help with studying?", true)
                        .addChoice("Reset Notes", "resetnotes")
                        .addChoice("Add Question", "addquestion")
                        .addChoice("Study", "study")
                        .addChoice("Save Quiz ", "savenotes")
                        .addChoice("Load Quiz", "loadnotes"));
    }
}
