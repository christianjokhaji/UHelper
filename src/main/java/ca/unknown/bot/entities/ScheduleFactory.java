package ca.unknown.bot.entities;

import java.util.ArrayList;

public interface ScheduleFactory {
    Schedule create();
    Schedule create(ArrayList<ScheduledEvent> events);
}
