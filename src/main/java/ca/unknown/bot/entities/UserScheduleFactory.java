package ca.unknown.bot.entities;
import java.util.*;

public class UserScheduleFactory implements ScheduleFactory{
    public Schedule create(){
        return new UserSchedule();
    }

    public Schedule create(ArrayList<ScheduledEvent> events){
        return new UserSchedule(events);
    }
}
