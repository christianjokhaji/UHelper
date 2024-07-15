package ca.unknown.bot.entities;
import java.util.*;

public class Schedule {

    private List<ScheduledEvent> events = new ArrayList<>();

    private String user;

    public Schedule(String user){
        this.user = user;
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

    public String getUser(){
        return user;
    }

    public void printSchedule(){
        sort();
        System.out.println("Here is your upcoming schedule: \n");
        for(ScheduledEvent s: events){
            System.out.println(s.toString() + "\n");
        }
    }

    public void clearSchedule(){
        events.clear();
    }
}
