package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * A subordinate use case interactor which schedules a new exam event.
 */
public class ScheduledExamInteractor extends ScheduledEventInteractor {
    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public ScheduledExamInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public ScheduledEvent execute(ScheduledReminderInputData scheduledReminderInputData, String user, SlashCommandInteractionEvent event) {
        ScheduledEvent schedExam = eventFactory.createExam(scheduledReminderInputData.getEventDate(),
                scheduledReminderInputData.getEventName(), scheduledReminderInputData.getLocation());
        scheduleDAO.getSchedule(user).addEvent(schedExam);
        scheduleDAO.addExistingCheck(user, schedExam.getEventName());
        scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
        event.getHook().sendMessage("You have scheduled the following event: \n"+ schedExam.toString()).queue();
        return schedExam;
    }
}
