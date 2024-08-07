package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

/**
 * <code>ScheduledEvent</code> is the base class for all events scheduled by the user and stores
 * a generic event.
 */
public class ScheduledEvent implements Comparable<ScheduledEvent> {
    private final Date eventDate;
    private final String eventName;

    /**
     * Class constructor.
     * @param eventDate date of this event
     * @param eventName name of this event
     */
    public ScheduledEvent(Date eventDate, String eventName){
        this.eventDate = eventDate;
        this.eventName = eventName;
    }

    /**
     * Returns the date of this event.
     * @return a <code>Date</code> object representation of this event's date
     */
    public Date getEventDate(){
        return eventDate;
    }


    /**
     * Returns the name of this event.
     * @return this event's name
     */
    public String getEventName(){
        return eventName;
    }

    /**
     * Compares two scheduled events for chronological order based on <code>EventDate</code> properties.
     * @param e the scheduled event to be compared to
     * @return a negative integer, zero, or a positive integer as this scheduled event is before, at the same time,
     * or after the specified scheduled event
     */
    @Override
    public int compareTo(ScheduledEvent e){
        return getEventDate().compareTo(e.getEventDate());
    }

    /**
     * Returns a reminder statement for when this event is scheduled that can then be DM'd to the user.
     * @return a String representation of this <code>ScheduledEvent</code>'s reminder alert
     */
    public String reminderAlert(){
        return "Reminder! You have an event '" + eventName +  "' on " + eventDate;
    }

    /**
     * Returns a String representation of this scheduled event's name and date details.
     * @return a String representation of this <code>ScheduledEvent</code>
     */
    @Override
    public String toString(){
        return "Event: " + eventName + " \t Date: " + eventDate;
    }
}
