package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.Schedule;

import java.util.*;

public class ScheduledReminderDAO implements ScheduledReminderDataAccessInterface {
    private Map<String, Schedule> userSchedules = new HashMap<>();

    public ScheduledReminderDAO(){

    }

    @Override
    public boolean existsByUser(String user) {
        return userSchedules.containsKey(user);
    }

    @Override
    public void save(Schedule sched) {
        userSchedules.put(sched.getUser(), sched);
        this.save();
    }

    public Schedule getSchedule(String user){
        return userSchedules.get(user);
    }

    private void save(){

    }

    private void load(){
        // put in map from file
    }
}