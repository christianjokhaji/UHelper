package ca.unknown.bot.entities.schedule_reminder;

import ca.unknown.bot.entities.schedule_reminder.Schedule;

public interface ScheduleFactory {
    Schedule create(String user);
}
