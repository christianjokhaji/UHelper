package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.Schedule;

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
     * Returns a user's schedule from the database.
     * @param user the user whose <code>Schedule</code> is to be returned
     * @return this user's <code>Schedule</code>
     */
    Schedule getSchedule(String user);
}
