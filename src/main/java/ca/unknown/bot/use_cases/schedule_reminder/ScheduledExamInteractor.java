package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;

public class ScheduledExamInteractor extends ScheduledEventInteractor {
    public ScheduledExamInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public void execute(ScheduledReminderInputData scheduledReminderInputData, String user) {
        ScheduledEvent schedEvent = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName(),
                scheduledReminderInputData.getLocation());
        scheduleDAO.getSchedule(user).addEvent(schedEvent);
    }
}
