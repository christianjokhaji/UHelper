package ca.unknown.bot.entities.schedule_reminder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    private Exam exam;

    @BeforeEach
    void setUp() {
        EventDate date = new EventDate(2024, 8 - 1,15, 19, 00, 00);
        this.exam = new Exam(date.getDate(), "CSC207", "Exam Centre");
    }

    @Test
    void getEventDate() {
        Calendar.Builder dateBuilder = new Calendar.Builder();
        dateBuilder.setDate(2024, 8 - 1, 15);
        dateBuilder.setTimeOfDay(19, 00, 00);
        Date date = dateBuilder.build().getTime();

        assertEquals(date, exam.getEventDate());
    }

    @Test
    void getCourseCode() {
        assertEquals("CSC207", exam.getEventName());
    }

    @Test
    void getLocation() {
        assertEquals("Exam Centre", exam.getLocation());
    }

    @Test
    void reminderAlert() {
        String tester = "Reminder! Your exam for CSC207 is at Exam Centre on Thu Aug 15 19:00:00 EDT 2024";

        assertEquals(tester, exam.reminderAlert());
    }

    @Test
    void testToString() {
        String tester = "Exam: CSC207" + " \t Location: Exam Centre" + " \t Date: Thu Aug 15 19:00:00 EDT 2024";

        assertEquals(tester, exam.toString());
    }
}