package ca.unknown.bot.commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

/**
 * CommandRegistrar is responsible for registering all the slash commands for the bot.
 * It gathers commands from various command classes(group by features) and registers them with the
 * JDA instance.
 */

public class CommandManager {
    /**
     * Registers all the commands with the provided JDA instance.
     *
     * @param jda The JDA instance to register the commands with.
     */
    public static void registerCommands(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                WikiCommands.getWikiCommands(),
                GameCommands.getRockPaperScissorsCommand(),
                GameCommands.getTriviaCommand(),
                GameCommands.get8BallCommand(),
                RecipeCommands.getFindRecipesCommand(),
                TimerCommands.getTimerCreateCommand(),
                TimerCommands.getTimerDeleteCommand(),
                TimerCommands.getTimerListCommand(),
                TimerCommands.getTimerStartCommand(),
                TimerCommands.getTimerCancelCommand(),
                ScheduledRemindersCommands.getScheduleExamCommand(),
                ScheduledRemindersCommands.getScheduleAssignmentCommand(),
                ScheduledRemindersCommands.getScheduleEventCommand(),
                ScheduledRemindersCommands.getCurrentScheduleCommand(),
                ScheduledRemindersCommands.getClearEventCommand(),
                ScheduledRemindersCommands.getClearScheduleCommand(),
                StudyHelpCommands.getStudyHelpCommand()
        ).queue();
    }
}