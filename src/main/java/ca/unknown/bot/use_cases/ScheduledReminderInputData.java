package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.EventDate;

/**
 * Converts user input data to a format that the interactor can use.
 * @param eventName Stores the name of a scheduled event. This would refer to the course code for an Exam
 *                  and the assignment name for an Assignment.
 * @param eventDate Stores the date of a scheduled event in YY MM DD HR MIN SEC format. Refers to the
 *                  exam date for an Exam and due date for an Assignment.
 * @param location Stores the location of an Exam.
 * @param assignmentCourseCode Stores the course code of an Assignment.
 *
 */
public class ScheduledReminderInputData {
    private String eventName;
    private EventDate eventDate;
    private String location;
    private String assignmentCourseCode;

    public ScheduledReminderInputData(String eventName, int[] eventDateNumeric){
        this.eventName = eventName;
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]);
    }

    public ScheduledReminderInputData(String courseCode, int[] eventDateNumeric, String location){
        this.eventName = courseCode;
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]);
        this.location = location;
    }

    public ScheduledReminderInputData(String assignmentName, String courseCode, int[] eventDateNumeric){
        this.eventName = assignmentName;
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]);
        this.assignmentCourseCode = courseCode;
    }


    public String getEventName(){
        return eventName;
    }

    public EventDate getEventDate(){
        return eventDate;
    }

    public String getLocation(){
        return location;
    }

    public String getAssignmentCourseCode(){
        return assignmentCourseCode;
    }

}
