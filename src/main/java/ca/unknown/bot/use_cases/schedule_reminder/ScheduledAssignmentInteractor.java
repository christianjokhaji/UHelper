package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * A subordinate use case interactor which schedules a new assignment event.
 */
public class ScheduledAssignmentInteractor extends ScheduledEventInteractor {
    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public ScheduledAssignmentInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public ScheduledEvent execute(ScheduledReminderInputData scheduledReminderInputData, String user, SlashCommandInteractionEvent event) {
        ScheduledEvent schedAssignment = eventFactory.createAssignment(scheduledReminderInputData.getEventDate(),
                scheduledReminderInputData.getEventName(), scheduledReminderInputData.getAssignmentCourseCode());
        scheduleDAO.getSchedule(user).addEvent(schedAssignment);
        scheduleDAO.addExistingCheck(user, schedAssignment.getEventName());
        scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
        event.getHook().sendMessage("You have scheduled the following event: \n"+ schedAssignment.toString()).queue();
        return schedAssignment;
    }
}
