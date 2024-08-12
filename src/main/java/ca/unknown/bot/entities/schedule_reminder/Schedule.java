package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

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
     * Removes a passed event from this schedule.
     */
    void removePassedEvent(ScheduledEvent s);

    /**
     * Returns the number of events in this schedule.
     * @return an int of the number of events in this <code>Schedule</code>
     */
    int getSize();

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
     * @param eventIndex the index of the event to clear
     * @return the name of the event that was cleared
     */
    String clearSingle(int eventIndex);

    /**
     * Returns the discord username of the user that this schedule belongs to.
     * @return the username of this <code>Schedule</code>
     */
    String getUser();

    /**
     * Returns the discord ID of the user that this schedule belongs to.
     * @return the userID of this <code>Schedule</code>
     */
    long getUserId();

    /**
     * Returns whether this schedule has any upcoming events.
     * @return true if this <code>Schedule</code> is empty
     */
    boolean hasNoEvents();

    /**
     * Returns whether this schedule has the given event.
     * @param s the event to search for
     * @return true if the scheduled event is found in this <code>Schedule</code>
     */
    boolean hasEvent(ScheduledEvent s);


    /**
     * Returns whether this schedule already contains a specified event by name and date.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @return true if the specified event is in this schedule
     */
    boolean hasDuplicateEvent(String eventName, Date eventDate);

    /**
     * Returns whether this schedule already has an exam scheduled at the given time.
     * @param eventDate the date to search by
     * @return true if the user already has an exam scheduled at this time
     */
    boolean hasDuplicateExam(Date eventDate);

    /**
     * Returns whether this schedule already has an assignment by the given name.
     * @param assignmentName the name to search by
     * @param courseCode the class which the assignment is from
     * @return true if the user already has scheduled this assignment
     */
    boolean hasDuplicateAssignment(String assignmentName, String courseCode);

    /**
     * Returns a list of the user's <code>ScheduledEvent</code>s
     * @return a list of <code>ScheduledEvent</code>s
     */
    List<ScheduledEvent> getEvents();
}
