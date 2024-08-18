package ca.unknown.bot.entities.schedule_reminder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserScheduleTest {
    private Schedule userSchedule;

    @BeforeEach
    void init() {
        EventDate date = new EventDate(2024, 3,29, 11, 55, 00);
        ScheduledEvent event1 = new ScheduledEvent(date.getDate(), "my birthday");

        this.userSchedule = new UserSchedule("luvsetsuna", 310485751080943617L);
        userSchedule.addEvent(event1);
    }

    @Test
    void getUser() {
        assertEquals("luvsetsuna", userSchedule.getUser());
    }

    @Test
    void getUserId() {
        assertEquals(310485751080943617L, userSchedule.getUserId());
    }

    @Test
    void getSize(){
        assertEquals(1, userSchedule.getSize());
    }

    @Test
    void checkAddEvent(){
        EventDate date = new EventDate(2024, 8,7, 20, 37, 00);
        ScheduledEvent event2 = new ScheduledEvent(date.getDate(), "today");

        userSchedule.addEvent(event2);
        assertEquals(2, userSchedule.getSize());
    }

    @Test
    void checkClearSingle(){
        userSchedule.clearSingle(0);
        assertEquals(true, userSchedule.hasNoEvents());
    }

    @Test
    void checkClearAll(){
        EventDate date = new EventDate(2024, 8,7, 20, 37, 00);
        ScheduledEvent event2 = new ScheduledEvent(date.getDate(), "today");

        userSchedule.addEvent(event2);
        userSchedule.clearSched();
        assertEquals(true, userSchedule.hasNoEvents());
    }

    @Test
    void checkHasDuplicateEvent(){
        EventDate date = new EventDate(2024, 3,29, 11, 55, 00);
        ScheduledEvent event = new ScheduledEvent(date.getDate(), "my birthday");

        assertEquals(true, userSchedule.hasDuplicateEvent(event.getEventName(), event.getEventDate()));
    }

    @Test
    void checkHasNoEvents(){
        assertEquals(false, userSchedule.hasNoEvents());
    }

    @Test
    void checkToString(){
        EventDate date = new EventDate(2024, 3,29, 11, 55, 00);
        ScheduledEvent event = new ScheduledEvent(date.getDate(), "my birthday");

        String tester = "Here is your upcoming schedule: \n" + "1. " + event.toString() + "\n";

        assertEquals(tester, userSchedule.toString());
    }
}
