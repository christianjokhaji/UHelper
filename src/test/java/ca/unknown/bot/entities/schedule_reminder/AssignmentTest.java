package ca.unknown.bot.entities.schedule_reminder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    private Assignment assignment;

    @BeforeEach
    void init() {
        EventDate date = new EventDate(2024, 8 - 1,15, 23, 59, 00);
        this.assignment = new Assignment(date.getDate(), "Phase two", "CSC207");
    }

    @Test
    void getEventDate() {
        Calendar.Builder dateBuilder = new Calendar.Builder();
        dateBuilder.setDate(2024, 8 - 1, 15);
        dateBuilder.setTimeOfDay(23, 59, 00);
        Date date = dateBuilder.build().getTime();

        assertEquals(date, assignment.getEventDate());
    }

    @Test
    void getAssignmentName() {
        assertEquals("Phase two", assignment.getEventName());
    }

    @Test
    void getCourseCode(){
        assertEquals("CSC207", assignment.getCourseCode());
    }

    @Test
    void reminderAlertCheck() {
        String tester = "Reminder! Your assignment, 'Phase two', for class CSC207 is due on Thu Aug 15 23:59:00 EDT 2024";

        assertEquals(tester, assignment.reminderAlert());
    }

    @Test
    void toStringCheck(){
        String tester = "Assignment: Phase two" + " \t Class: CSC207" + " \t Due Date: Thu Aug 15 23:59:00 EDT 2024";

        assertEquals(tester, assignment.toString());
    }
}