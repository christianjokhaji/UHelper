package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

public class UserSchedule implements Schedule {

    private List<ScheduledEvent> events = new ArrayList<>();
    private String user;

    public UserSchedule(String user){
        this.user = user;
    }

//    public void addEvent(Exam e){
//        events.add(e);
//    }
//
//    public void addEvent(Assignment a){
//        events.add(a);
//    }

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

    public String getUser(){
        return user;
    }
}
