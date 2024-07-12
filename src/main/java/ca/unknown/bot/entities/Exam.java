package ca.unknown.bot.entities;
import java.util.Calendar;

/**
 * Stores the user's assignment details.
 * @param courseCode The course code of the class which the exam is for.
 * @param location The location of the exam.
 * @param examDate The date and time that the exam is scheduled at.
 */
public class Exam {
    private String courseCode;
    private String location;
    private Calendar examDate;

    public Exam(String courseCode, String location, Calendar examDate){
        this.courseCode = courseCode;
        this.location = location;
        this.examDate = examDate;
    }

    /**
     * Returns the course code of the class that the assignment is from.
     * @return
     */
    public String getCourseCode(){
        return courseCode;
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
    public String toString(){
        return "Your exam for " + courseCode + " is in the " + location +
                " on " + examDate.getTime();
    }
}
