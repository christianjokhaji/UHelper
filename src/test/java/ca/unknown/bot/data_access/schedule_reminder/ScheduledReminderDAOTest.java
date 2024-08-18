package ca.unknown.bot.data_access.schedule_reminder;

import ca.unknown.bot.entities.schedule_reminder.Exam;
import ca.unknown.bot.entities.schedule_reminder.Schedule;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import ca.unknown.bot.entities.schedule_reminder.UserSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ScheduledReminderDAOTest {

    private ScheduledReminderDataAccessInterface scheduleDAO;
    private String filename;
    private Schedule mockSchedule;
    private ScheduledEvent mockExam;
    private Date mockDate;

    @BeforeEach
    void init(){
        scheduleDAO = new ScheduledReminderDAO();
        filename = "src/test/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository_test.json";
        mockSchedule = new UserSchedule("luvsetsuna", 310485751080943617L);
        mockDate = new Calendar.Builder().setDate(2024,02,29).setTimeOfDay(11, 11, 00).build().getTime();
        mockExam = new Exam(mockDate, "CSC207", "Exam Centre");
        mockSchedule.addEvent(mockExam);
        scheduleDAO.saveNewUser(mockSchedule);
    }


    @Test
    void existsByUser() {
        assertEquals(true, scheduleDAO.existsByUser("luvsetsuna"));
    }


    @Test
    void saveNewUser() {
        Schedule otherSchedule = new UserSchedule("hurrikanelaw", 123123123123L);
        scheduleDAO.saveNewUser(otherSchedule);
        assertEquals(true, scheduleDAO.existsByUser("hurrikanelaw"));
    }

    @Test
    void repoTest() {
        scheduleDAO.saveToFile(filename);
        Map<String, Schedule> repoTest = scheduleDAO.getRepo();
        assertEquals(true, scheduleDAO.getRepo().equals(repoTest));
    }

    @Test
    void getSchedule() {
        assertEquals(true, scheduleDAO.getSchedule("luvsetsuna").equals(mockSchedule));
    }
}