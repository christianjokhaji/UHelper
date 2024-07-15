package ca.unknown.bot.entities;
import java.util.Calendar;

/**
 * Stores the user's exam details.
 * @param location The location of the exam.
 */
public class Exam extends ScheduledEvent {

    private String location;

    public Exam(Calendar examDate, String courseCode, String location){
        super(examDate, courseCode);
        this.location = location;
    }

    /**
     * Returns the location of the exam.
     * @return
     */
    public String getLocation(){
        return location;
    }

    /**
     * Print a reminder statement for when the exam is scheduled (incl. location and time).
     * @return
     */
    public String reminderAlert(){
        return "Reminder! Your exam for " + getEventName() + " is at " + location +
                " on " + getEventDate().getTime();
    }

    public String toString(){
        return "Exam: '" + getEventName() + "' \t Location: " + location +
                " \t Time: " + getEventDate().getTime();
    }
}
