package ca.unknown.bot.entities;

import java.util.Calendar;

public class ScheduledEvent implements Comparable<ScheduledEvent> {
    private Calendar eventDate;
    private String eventName;

    public ScheduledEvent(Calendar eventDate, String eventName){
        this.eventDate = eventDate;
        this.eventName = eventName;
    }


    public Calendar getEventDate(){
        return eventDate;
    }

    public String getEventName(){
        return eventName;
    }

    @Override
    public int compareTo(ScheduledEvent e){
        return getEventDate().compareTo(e.getEventDate());
    }

    public String reminderAlert(){
        return "Reminder! You have '" + eventName +  "' on " + eventDate.getTime();
    }

    public String toString(){
        return "Event: '" + getEventName() + "' \t Time: " + getEventDate().getTime();
    }
}
