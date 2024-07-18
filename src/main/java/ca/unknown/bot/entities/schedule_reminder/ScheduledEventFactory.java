package ca.unknown.bot.entities.schedule_reminder;

public class ScheduledEventFactory {

    public ScheduledEvent create(EventDate eventDate, String eventName){
        return new ScheduledEvent(eventDate, eventName);
    }

    public ScheduledEvent create(EventDate eventDate, String eventName, String location){
        return new Exam(eventDate, eventName, location);
    }

    public ScheduledEvent create(String courseCode, EventDate eventDate, String eventName){
        return new Assignment(eventDate, eventName, courseCode);
    }
}
