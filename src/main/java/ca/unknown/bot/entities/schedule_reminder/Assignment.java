package ca.unknown.bot.entities.schedule_reminder;
import java.util.*;

/**
 * Stores the user's assignment details.
 */
public class Assignment extends ScheduledEvent {
    /**
     * Course code of the class which this <code>Assignment</code> is from.
     */
    private String courseCode;

    /**
     * Class constructor.
     * @param dueDate the due date of this assignment
     * @param assignmentName the name of this assignment
     * @param courseCode the course code of the class that this assignment is from
     */
    public Assignment(Date dueDate, String assignmentName, String courseCode){
        // assignmentName = eventName in superclass
        super(dueDate, assignmentName);
        this.courseCode = courseCode;
    }

    /**
     * Returns the course code of the class that this assignment is from.
     * @return the <code>courseCode</code> of this <code>Assignment</code>
     */
    public String getCourseCode(){
        return courseCode;
    }


    /**
     * Returns a reminder statement for when this assignment is due that can then be DM'd to the user.
     * @return a String representation of this <code>Assignment</code>'s reminder alert
     */
    public String reminderAlert(){
        return "Reminder! Your assignment, '" + getEventName() + "', for class " + courseCode +
                " is due on " + getEventDate();
    }

    /**
     * Returns a String representation of this assignment's name, due date, and course code details.
     * @return a String representation of this <code>Assignment</code>
     */
    public String toString(){
        return "Assignment: " + getEventName() + " \t Class: " + courseCode +
                " \t Due Date: " + getEventDate();
    }
}

