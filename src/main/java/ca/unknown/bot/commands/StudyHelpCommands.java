package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class StudyHelpCommands {
    public static SlashCommandData getStudyHelpCommand() {
        return Commands.slash("study-help", "Get Study Help!")
                .addOptions(new OptionData(OptionType.STRING, "choice",
                        "How can we help with studying?")
                        .addChoice("Reset Notes", "resetnotes")
                        .addChoice("Add Question", "addquestion")
                        .addChoice("Study", "study")
                        .addChoice("Save Quiz ", "savenotes")
                        .addChoice("Load Quiz", "loadnotes"));
    }
}
