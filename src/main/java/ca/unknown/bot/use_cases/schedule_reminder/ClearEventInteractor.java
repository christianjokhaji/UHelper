package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;

public class ClearEventInteractor {
    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;

    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public ClearEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    public String execute(String username, int index){
        // clear the event
        String eventName = scheduleDAO.getSchedule(username).clearSingle(index - 1);

        // remove reminder alert for that event
        scheduleDAO.removeCheck(username, eventName);

        // update repo
        scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");

        return eventName;
    }
}
