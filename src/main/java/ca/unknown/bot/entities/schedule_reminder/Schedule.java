package ca.unknown.bot.entities.schedule_reminder;

public interface Schedule {

    void addEvent(ScheduledEvent s);

    void sort();

    String toString();

    void clearSched();

    String getUser();

    boolean hasNoEvents();
}
