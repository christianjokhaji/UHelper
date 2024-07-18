package ca.unknown.bot.entities.schedule_reminder;

import ca.unknown.bot.entities.schedule_reminder.Schedule;
import ca.unknown.bot.entities.schedule_reminder.ScheduleFactory;
import ca.unknown.bot.entities.schedule_reminder.UserSchedule;

public class UserScheduleFactory implements ScheduleFactory {
    public Schedule create(String user){
        return new UserSchedule(user);
    }
}
