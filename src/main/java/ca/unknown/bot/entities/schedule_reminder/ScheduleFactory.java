package ca.unknown.bot.entities.schedule_reminder;

/**
 * Abstraction of a factory class which creates a new schedule.
 */
public interface ScheduleFactory {
    /**
     * Creates a new schedule for a desired user.
     * @param user the user of this schedule
     * @return a new <code>Schedule</code> object
     */
    Schedule create(String user);
}
