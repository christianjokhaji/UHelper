package ca.unknown.bot.entities.schedule_reminder;

/**
 * Abstraction of a user schedule which stores a collection of scheduled events and manipulates them.
 */
public interface Schedule {

    /**
     * Adds a scheduled event to this schedule.
     * @param s the scheduled event which should be added to this schedule
     */
    void addEvent(ScheduledEvent s);

    /**
     * Sorts this schedule for chronological order of dates.
     */
    void sort();

    /**
     * Returns a String representation of all the events in this schedule.
     * @return a String representation of this <code>Schedule</code>
     */
    String toString();

    /**
     * Clears all the events from this schedule.
     */
    void clearSched();

    /**
     * Clears a single event from this schedule.
     * @param eventName the event to clear
     */
    void clearSingle(String eventName);

    /**
     * Returns the user that this schedule belongs to.
     * @return the user of this <code>Schedule</code>
     */
    String getUser();

    /**
     * Returns whether this schedule has any upcoming events.
     * @return true if this <code>Schedule</code> is empty
     */
    boolean hasNoEvents();

    /**
     * Returns whether this schedule contains a specified event.
     * @param eventName the event to search for
     * @return true if the specified event is in this schedule
     */
    boolean hasEvent(String eventName);
}
