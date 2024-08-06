package ca.unknown.bot.commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class CommandManager {
    public static void registerCommands(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                RecipeCommands.getFindRecipesCommand(),
                GameCommands.getRockPaperScissorsCommand(),
                GameCommands.getTriviaCommand(),
                GameCommands.get8BallCommand(),
                TimerCommands.getTimerCreateCommand(),
                TimerCommands.getTimerDeleteCommand(),
                TimerCommands.getTimerListCommand(),
                TimerCommands.getTimerStartCommand(),
                TimerCommands.getTimerCancelCommand(),
                ScheduleReminderCommands.getScheduleExamCommand(),
                ScheduleReminderCommands.getScheduleAssignmentCommand(),
                ScheduleReminderCommands.getScheduleEventCommand(),
                ScheduleReminderCommands.getCurrentScheduleCommand(),
                ScheduleReminderCommands.getClearScheduleCommand(),
                StudyHelpCommands.getStudyHelpCommand()
        ).queue();
    }
}