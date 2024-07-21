package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.Schedule;

public interface ScheduledReminderDataAccessInterface {
    boolean existsByUser(String user);

    void saveNewUser(Schedule userSchedule);

    void saveToFile(String filename);

    Schedule getSchedule(String user);
}
