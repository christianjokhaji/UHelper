package ca.unknown.bot.entities.schedule_reminder;
import java.util.*;

/**
 * Stores custom event dates and is sortable by <code>Date</code> properties.
 */
public class EventDate implements Comparable<EventDate> {

    /**
     * <code>Calendar</code> object storing this event date.
     */
    private Calendar.Builder date = new Calendar.Builder();

    /**
     * Class constructor.
     * @param year the year of this event
     * @param month the month of this event
     * @param day the day of this event
     * @param hour the military hour of this event
     * @param min the minutes value of this event's time
     * @param sec the seconds value of this event's time
     */
    public EventDate(int year, int month, int day, int hour, int min, int sec){
        date.setDate(year, month, day);
        date.setTimeOfDay(hour, min, sec);
    }

    /**
     * Returns the date of this event.
     * @return a <code>Date</code> object storing the date of this event
     */
    public Date getDate(){
            return date.build().getTime();
    }

    /**
     * Compares two event dates for chronological order.
     * @param e the event date to be compared to
     * @return a negative integer, zero, or a positive integer as this date is before, at the same time,
     * or after the specified event date
     */
    @Override
    public int compareTo(EventDate e){
        return getDate().compareTo(e.getDate());
    }
}
