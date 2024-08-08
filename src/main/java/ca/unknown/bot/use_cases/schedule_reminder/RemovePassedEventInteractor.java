package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;

import java.util.*;
import java.util.concurrent.*;

public class RemovePassedEventInteractor {
    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public RemovePassedEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    public void execute(String user, ScheduledEvent scheduledEvent){
        long currentDate = new Date().getTime();
        long eventDate = scheduledEvent.getEventDate().getTime();
        long delay = eventDate - currentDate;

        Runnable removeEvent = () -> this.remove(user, scheduledEvent);
        scheduler.schedule(removeEvent, delay, TimeUnit.MILLISECONDS);
    }

    private void remove(String user, ScheduledEvent s){
        if(scheduleDAO.getSchedule(user).hasEvent(s)){
            scheduleDAO.getSchedule(user).removePassedEvent(s);
        }
    }
}
