package ca.unknown.bot.entities.schedule_reminder;

public class ScheduledEventFactory {

    public ScheduledEvent createEvent(EventDate eventDate, String eventName){
        return new ScheduledEvent(eventDate, eventName);
    }

    public ScheduledEvent createExam(EventDate eventDate, String eventName, String location){
        return new Exam(eventDate, eventName, location);
    }

    public ScheduledEvent createAssignment(EventDate eventDate, String eventName, String courseCode){
        return new Assignment(eventDate, eventName, courseCode);
    }
}
