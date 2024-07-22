package ca.unknown.bot.entities.schedule_reminder;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Implements InstanceCreator to help convert my Schedule interface to gson from json.
 */
public class ScheduleInstanceCreator implements InstanceCreator<Schedule> {
    private String user;

    public ScheduleInstanceCreator(String user){
        this.user = user;
    }

    @Override
    public Schedule createInstance(Type type) {
        return new UserSchedule(this.user);
    }
}
