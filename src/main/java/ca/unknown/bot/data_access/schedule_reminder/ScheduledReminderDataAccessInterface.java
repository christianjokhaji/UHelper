package ca.unknown.bot.data_access.schedule_reminder;

import ca.unknown.bot.entities.schedule_reminder.Schedule;

import java.util.Map;

/**
 * An abstraction for the DAO which interacts with the Data Access Layer for the schedule reminder
 * use case.
 */
public interface ScheduledReminderDataAccessInterface {
    /**
     * Returns whether a given user has an already existing schedule in the cache.
     * @param user the user of the schedule that is being searched for
     * @return true if the user has an existing schedule in the cache
     */
    boolean existsByUser(String user);

    /**
     * Returns whether the current cache is empty.
     * @param filename the .json file to compare the current cache to
     * @return true if the in memory DAO contains no stored schedules
     */
    boolean emptyCache(String filename);

    /**
     * Saves a new user schedule to the cache.
     * @param userSchedule the new user's schedule to be saved
     */
    void saveNewUser(Schedule userSchedule);

    /**
     * Saves the current cache of schedules to a .json file.
     * @param filename the .json file to persist to
     */
    void saveToFile(String filename);

    /**
     * Loads a saved schedule repo onto the cache.
     * @param filename the .json file to load from
     */
    void loadRepo(String filename);

    /**
     * Returns whether a user should receive a direct message alert for the specified event.
     * @param user the user who is to receive a direct message
     * @param eventName a user scheduled event
     * @return true if the user should receive a direct message reminder for the event
     */
    boolean getChecks(String user, String eventName);

    /**
     * Adds the specified event to an already existing reminder alert list for the user.
     * @param user the user who should receive an alert
     * @param eventName the event for which the user should receive an alert for
     */
    void addExistingCheck(String user, String eventName);

    /**
     * Opens a new list of reminder alerts for the user when the program restarts.
     * @param user the user who should receive alerts
     */
    void addNewCheck(String user);

    /**
     * Unsubscribes the user from receiving a reminder alert for the specified event.
     * @param user the user who needs to be unsubscribed
     * @param eventName the event for which the user should be unsubscribed from
     */
    void removeCheck(String user, String eventName);

    /**
     * Unsubscribes the user from all current reminder alerts.
     * @param user the user whose alerts should be cleared
     */
    void removeAllChecks(String user);

    /**
     * Returns a user's schedule from the database.
     * @param user the user whose <code>Schedule</code> is to be returned
     * @return this user's <code>Schedule</code>
     */
    Schedule getSchedule(String user);

    /**
     * Returns the current cache of the program.
     * @return this program's schedule repository
     */
    Map<String, Schedule> getRepo();

}
