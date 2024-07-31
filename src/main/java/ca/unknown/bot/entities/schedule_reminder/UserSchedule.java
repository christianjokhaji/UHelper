package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

/**
 * Concrete implementation of a user <code>Schedule</code>. Stores scheduled events and manipulates them.
 */
public class UserSchedule implements Schedule {

    /**
     * A list of this schedule's current events.
     */
    private List<ScheduledEvent> events = new ArrayList<>();

    /**
     * The user of this schedule.
     */
    private String user;

    /**
     * Class constructor.
     * @param user the user of this schedule
     */
    public UserSchedule(String user){
        this.user = user;
    }

    public void addEvent(ScheduledEvent s){
        events.add(s);
    }

    public void sort(){
        Collections.sort(events);
    }

    public String toString(){
        sort();
        StringBuilder output = new StringBuilder("Here is your upcoming schedule: \n");
        String temp = "";
        for(ScheduledEvent s: events){
            temp = s.toString() + "\n";
            output.append(temp);
        }
        return output.toString();
    }

    public void clearSched(){
        events.clear();
    }

    public void clearSingle(String eventName){
        for(ScheduledEvent s: events){
            if(s.getEventName().equals(eventName)){
                events.remove(s);
                break;
            }
        }
    }

    public String getUser(){
        return user;
    }

    public boolean hasNoEvents(){
        return events.isEmpty();
    }

    public boolean hasEvent(String eventName){
        for(ScheduledEvent s: events){
            if(s.getEventName().equals(eventName)){
                return true;
            }
        }
        return false;
    }
}
