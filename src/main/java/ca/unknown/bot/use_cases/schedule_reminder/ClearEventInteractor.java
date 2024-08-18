package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;

/**
 * A subordinate use case interactor which clears individual events from the user's schedule.
 */
public class ClearEventInteractor {
    /**
     * The current DAO in use by the program.
     */
    final ScheduledReminderDataAccessInterface scheduleDAO;

    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public ClearEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    /**
     * Removes the specified event from the user's schedule via an indexed list and then unsubscribes them from the associated
     * reminder alert.
     * @param username the discord username of the user
     * @param index the index of the event they wish to clear
     * @return the name of the event which was removed
     */
    public String execute(String username, int index){
        String eventName = scheduleDAO.getSchedule(username).clearSingle(index - 1);

        scheduleDAO.removeCheck(username, eventName);

        scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");

        return eventName;
    }
}
