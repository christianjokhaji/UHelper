package ca.unknown.bot.entities.schedule_reminder;

public interface Schedule {
//    void addEvent(Exam e);
//
//    void addEvent(Assignment a);

    void addEvent(ScheduledEvent s);

    void sort();

    String toString();

    void clearSched();

    String getUser();
}
