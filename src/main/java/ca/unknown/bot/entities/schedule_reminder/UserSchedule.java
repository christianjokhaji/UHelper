package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

/**
 * Concrete implementation of a user <code>Schedule</code>. Stores scheduled events and manipulates them.
 */
public class UserSchedule implements Schedule {
    private List<ScheduledEvent> events = new ArrayList<>();
    private final String username;
    private final long userID;

    /**
     * Class constructor.
     * @param username the username of the user of this schedule
     * @param userID the discord userID of the user of this schedule
     */
    public UserSchedule(String username, long userID){
        this.username = username;
        this.userID = userID;
    }

    public void addEvent(ScheduledEvent s){
        events.add(s);
    }

    public void removePassedEvent(ScheduledEvent s){
        events.remove(s);
    }

    public void sort(){
        Collections.sort(events);
    }

    public int size(){
        return events.size();
    }

    public String toString(){
        sort();
        StringBuilder output = new StringBuilder("Here is your upcoming schedule: \n");
        String temp = "";
        int i = 1;
        for(ScheduledEvent s: events){
            temp = i + ". " + s.toString() + "\n";
            output.append(temp);
            i++;
        }
        return output.toString();
    }

    public void clearSched(){
        events.clear();
    }

    public String clearSingle(int index){
        String eventName = events.get(index).getEventName();
        events.remove(index);

        return eventName;
    }

    public String getUser(){
        return username;
    }

    public boolean hasNoEvents(){
        return events.isEmpty();
    }

    public boolean hasEvent(ScheduledEvent s){
        return events.contains(s);
    }

    public boolean hasDuplicateEvent(String eventName, Date eventDate){
        for(ScheduledEvent s: events){
            if(!(s instanceof Exam || s instanceof Assignment))
                if(s.getEventName().equals(eventName) && s.getEventDate().equals(eventDate)){
                return true;
            }
        }
        return false;
    }

    public boolean hasDuplicateExam(Date examDate){
        for(ScheduledEvent s: events){
            if(s instanceof Exam && s.getEventDate().equals(examDate)){
                return true;
            }
        }
        return false;
    }

    public boolean hasDuplicateAssignment(String assignmentName, String courseCode){
        for(ScheduledEvent s: events){
            if(s instanceof Assignment && s.getEventName().equals(assignmentName) &&
                    ((Assignment) s).getCourseCode().equals(courseCode)){
                return true;
            }
        }
        return false;
    }

    public List<ScheduledEvent> getEvents(){
        return this.events;
    }

    public long getUserId(){
        return this.userID;
    }
}
