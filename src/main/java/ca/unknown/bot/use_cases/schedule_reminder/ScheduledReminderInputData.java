package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.entities.schedule_reminder.EventDate;

import java.util.*;

/**
 * Stores inputted event data in a format that the DAO can use.
 */
public class ScheduledReminderInputData {
    private final String eventName;
    private final Date eventDate;
    private String location;
    private String assignmentCourseCode;


    /**
     * Class constructor for a generic scheduled event.
     * @param eventName the name of this event
     * @param eventDateNumeric the date of this event stored as an integer array to be converted to an
     *                         <code>Date</code> object
     */
    public ScheduledReminderInputData(String eventName, int[] eventDateNumeric){
        this.eventName = eventName;

        // month must subtract 1 because the Date class stores months starting at 00 (so Jan = 00 instead of 01)
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]).getDate();
    }

    /**
     * Class constructor for a scheduled exam event.
     * @param courseCode the course code of this exam
     * @param eventDateNumeric the date of this exam stored as an integer array to be converted to an
     *                         <code>Date</code> object
     * @param location  the location of this exam
     */
    public ScheduledReminderInputData(String courseCode, int[] eventDateNumeric, String location){
        this.eventName = courseCode;

        // month must subtract 1 because the Date class stores months starting at 00 (so Jan = 00 instead of 01)
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]).getDate();
        this.location = location;
    }

    /**
     * Class constructor for a scheduled assignment event.
     * @param assignmentName the name of this assignment
     * @param courseCode the course code of the class this assignment is from
     * @param eventDateNumeric the due date of this assignment stored as an integer array to be converted to an
     *      *                         <code>Date</code> object
     */
    public ScheduledReminderInputData(String assignmentName, String courseCode, int[] eventDateNumeric){
        this.eventName = assignmentName;

        // month must subtract 1 because the Date class stores months starting at 00 (so Jan = 00 instead of 01)
        this.eventDate = new EventDate(eventDateNumeric[0], eventDateNumeric[1] - 1, eventDateNumeric[2],
                eventDateNumeric[3], eventDateNumeric[4], eventDateNumeric[5]).getDate();
        this.assignmentCourseCode = courseCode;
    }


    /**
     * Returns the name of this event.
     * @return the name of this event
     */
    public String getEventName(){
        return eventName;
    }

    /**
     * Returns the date of this event.
     * @return an <code>Date</code> object corresponding to the date of this event
     */
    public Date getEventDate(){
        return eventDate;
    }

    /**
     * Returns the location of this exam event.
     * @return the location of this exam
     */
    public String getLocation(){
        return location;
    }

    /**
     * Returns the name of this assignment event.
     * @return the name of this assignment
     */
    public String getAssignmentCourseCode(){
        return assignmentCourseCode;
    }

}
