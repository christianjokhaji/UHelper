package ca.unknown.bot.entities;
import java.util.Calendar;

public class Assignment {
    private String classCode;
    private String assignmentName;
    private Calendar dueDate;

    public Assignment(String classCode, String assignmentName, Calendar dueDate){
        this.classCode = classCode;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }

    public String getClassCode(){
        return classCode;
    }

    public String getAssignmentName(){
        return assignmentName;
    }

    public Calendar getDueDate(){
        return dueDate;
    }

    public String toString(){
        return "Your assignment, '" + assignmentName + "', for class " + classCode +
                " is due on " + dueDate.getTime();
    }
}

