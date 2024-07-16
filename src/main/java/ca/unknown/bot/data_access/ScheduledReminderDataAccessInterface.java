package ca.unknown.bot.data_access;
import ca.unknown.bot.entities.Schedule;

public interface ScheduledReminderDataAccessInterface {
    boolean existsByUser(String user);

    void save(Schedule userSchedule);
}
