package ca.unknown.bot.entities;

/**
 * Stores the user's assignment details.
 * @param courseCode The course code of the class which the assignment is from.
 */
public class Assignment extends ScheduledEvent{
    private String courseCode;

    public Assignment(EventDate dueDate, String assignmentName, String courseCode){
        super(dueDate, assignmentName);
        this.courseCode = courseCode;
    }

    /**
     * Returns the course code of the class that the assignment is from.
     * @return
     */
    public String getCourseCode(){
        return courseCode;
    }


    /**
     * Print a reminder statement for when the assignment is due (incl. date and time).
     * @return
     */
    public String reminderAlert(){
        return "Reminder! Your assignment, '" + getEventName() + "', for class " + courseCode +
                " is due on " + getEventDate();
    }

    public String toString(){
        return "Assignment: '" + getEventName() + "' \t Class: " + courseCode +
                " \t Due Date: " + getEventDate();
    }
}

