package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;

public class ScheduledAssignmentInteractor extends ScheduledEventInteractor {
    public ScheduledAssignmentInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public void execute(ScheduledReminderInputData scheduledReminderInputData, String user) {
        ScheduledEvent schedEvent = eventFactory.create(scheduledReminderInputData.getAssignmentCourseCode(),
                scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName());
        scheduleDAO.getSchedule(user).addEvent(schedEvent);
    }
}
