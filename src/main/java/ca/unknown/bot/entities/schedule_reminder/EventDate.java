package ca.unknown.bot.entities.schedule_reminder;
import java.util.*;

/**
 * Creates Date objects that store custom event dates using Calendar Builder, to accommodate for
 * the deprecation of many Date functionalities.
 */
public class EventDate {
    private final Calendar.Builder date = new Calendar.Builder();

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

}
