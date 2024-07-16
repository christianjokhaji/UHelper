package ca.unknown.bot.entities;
import java.util.*;

public class UserSchedule implements Schedule {

    private List<ScheduledEvent> events;

    public UserSchedule(){
        events = new ArrayList<ScheduledEvent>();
    }

    public UserSchedule(ArrayList<ScheduledEvent> events){
        this.events = events;
    }

    public void addEvent(Exam e){
        events.add(e);
    }

    public void addEvent(Assignment a){
        events.add(a);
    }

    public void addEvent(ScheduledEvent s){
        events.add(s);
    }

    public void sort(){
        Collections.sort(events);
    }

    public String toString(){
        sort();
        String output = "Here is your upcoming schedule: \n";
        String temp = "";
        for(ScheduledEvent s: events){
            temp = s.toString() + "\n";
            output.concat(temp);
        }
        return output;
    }

    public void clearSched(){
        events.clear();
    }
}
