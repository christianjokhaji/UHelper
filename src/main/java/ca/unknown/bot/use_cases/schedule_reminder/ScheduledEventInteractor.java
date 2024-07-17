package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEventFactory;

public class ScheduledEventInteractor {
    ScheduledEventFactory eventFactory = new ScheduledEventFactory();
    ScheduledReminderDataAccessInterface scheduleDAO;


    public ScheduledEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    public void execute(ScheduledReminderInputData scheduledReminderInputData, String user){
        ScheduledEvent schedEvent = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName());
        scheduleDAO.getSchedule(user).addEvent(schedEvent);
    }
}