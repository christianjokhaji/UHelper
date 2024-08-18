package ca.unknown.bot.entities.schedule_reminder;
import java.util.*;

/**
 * Stores the user's exam details.
 */
public class Exam extends ScheduledEvent {
    private final String location;

    /**
     * Class constructor.
     * @param examDate the date of this exam
     * @param courseCode the course code of this exam
     * @param location the location of this exam
     */
    public Exam(Date examDate, String courseCode, String location){
        // courseCode = eventName in superclass
        super(examDate, courseCode);
        this.location = location;
    }

    /**
     * Returns the location of this exam.
     * @return the location of this exam
     */
    public String getLocation(){
        return location;
    }

    /**
     * Returns a reminder statement for when this exam is scheduled that can then be DM'd to the user.
     * @return a String representation of this <code>Exam</code>'s reminder alert
     */
    @Override
    public String reminderAlert(){
        return "Reminder! Your exam for " + getEventName() + " is at " + location +
                " on " + getEventDate();
    }

    /**
     * Returns a String representation of this exam's course code, scheduled time, and location details.
     * @return a String representation of this <code>Exam</code>
     */
    @Override
    public String toString(){
        return "Exam: " + getEventName() + " \t Location: " + location +
                " \t Date: " + getEventDate();
    }
}
