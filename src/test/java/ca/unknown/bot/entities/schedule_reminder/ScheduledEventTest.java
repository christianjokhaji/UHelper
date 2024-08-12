package ca.unknown.bot.entities.schedule_reminder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduledEventTest {

    private ScheduledEvent event;

    @BeforeEach
    void setUp() {
        EventDate date = new EventDate(2024, 8 - 1,12, 11, 11, 00);
        this.event = new ScheduledEvent(date.getDate(), "streaming");
    }

    @Test
    void getEventName() {
        assertEquals("streaming", event.getEventName());
    }

    @Test
    void getEventDate() {
        Calendar.Builder dateBuilder = new Calendar.Builder();
        dateBuilder.setDate(2024, 8 - 1, 12);
        dateBuilder.setTimeOfDay(11, 11, 00);
        Date date = dateBuilder.build().getTime();

        assertEquals(date, event.getEventDate());
    }

    @Test
    void compareToCheck(){
        Calendar.Builder dateBuilder = new Calendar.Builder();
        dateBuilder.setDate(2024, 8 - 1, 11);
        dateBuilder.setTimeOfDay(9, 00, 00);
        Date date = dateBuilder.build().getTime();
        ScheduledEvent testEvent = new ScheduledEvent(date, "badminton practice");

        assertEquals(1, event.compareTo(testEvent));
    }

    @Test
    void toStringCheck(){
        String tester = "Event: streaming " + "\t Date: Mon Aug 12 11:11:00 EDT 2024";

        assertEquals(tester, event.toString());
    }

    @Test
    void reminderAlertCheck(){
        String tester = "Reminder! You have an event 'streaming' on Mon Aug 12 11:11:00 EDT 2024";

        assertEquals(tester, event.reminderAlert());
    }
}
