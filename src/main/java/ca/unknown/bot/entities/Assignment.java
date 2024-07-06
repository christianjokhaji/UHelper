package ca.unknown.bot.entities;
import java.util.Calendar;

/**
 * Stores the user's assignment details.
 * @param classCode The code of the class which the assignment is from.
 * @param assignmentName The name of the assignment.
 * @param dueDate The date and time that the assignment is due.
 */
public class Assignment {
    private String classCode;
    private String assignmentName;
    private Calendar dueDate;

    public Assignment(String classCode, String assignmentName, Calendar dueDate){
        this.classCode = classCode;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }

    /**
     * Return the course code of the assignment.
     * @return
     */
    public String getClassCode(){
        return classCode;
    }

    /**
     * Return the name of the assignment.
     * @return
     */
    public String getAssignmentName(){
        return assignmentName;
    }

    /**
     * Return the due date of the assignment.
     * @return
     */
    public Calendar getDueDate(){
        return dueDate;
    }

    /**
     * Print a reminder statement for when the assignment is due (incl. date and time).
     * @return
     */
    public String toString(){
        return "Your assignment, '" + assignmentName + "', for class " + classCode +
                " is due on " + dueDate.getTime();
    }
}

