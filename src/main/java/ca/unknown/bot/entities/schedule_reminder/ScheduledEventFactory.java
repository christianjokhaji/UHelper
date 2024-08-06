package ca.unknown.bot.entities.schedule_reminder;

import java.util.*;

/**
 * Factory class which creates a new scheduled event: generic events, exams, and assignments.
 */
public class ScheduledEventFactory {

    /**
     * Creates a new scheduled event.
     * @param eventDate the date of this new event
     * @param eventName the name of this new event
     * @return a new <code>ScheduledEvent</code> object
     */
    public ScheduledEvent createEvent(Date eventDate, String eventName){
        return new ScheduledEvent(eventDate, eventName);
    }

    /**
     * Creates a new scheduled exam.
     * @param eventDate the date of this new exam
     * @param eventName the course code of this new exam
     * @param location the location of this new exam
     * @return a new <code>Exam</code> object
     */
    public ScheduledEvent createExam(Date eventDate, String eventName, String location){
        return new Exam(eventDate, eventName, location);
    }

    /**
     *
     * @param eventDate the due date of this new assignment
     * @param eventName the assignment name of this new assignment
     * @param courseCode the course code of the class which this assignment is from
     * @return a new <code>Assignment</code> object
     */
    public ScheduledEvent createAssignment(Date eventDate, String eventName, String courseCode){
        return new Assignment(eventDate, eventName, courseCode);
    }
}
