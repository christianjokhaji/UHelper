package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

public class ScheduledEvent implements Comparable<ScheduledEvent> {
    private EventDate eventDate;
    private String eventName;

    public ScheduledEvent(EventDate eventDate, String eventName){
        this.eventDate = eventDate;
        this.eventName = eventName;
    }

    public Date getEventDate(){
        return eventDate.getDate();
    }

    public String getEventName(){
        return eventName;
    }

    @Override
    public int compareTo(ScheduledEvent e){
        return getEventDate().compareTo(e.getEventDate());
    }

    public String reminderAlert(){
        return "Reminder! You have '" + eventName +  "' on " + eventDate.getDate();
    }

    public String toString(){
        return "Event: '" + eventName + "' \t Date: " + eventDate.getDate();
    }
}
