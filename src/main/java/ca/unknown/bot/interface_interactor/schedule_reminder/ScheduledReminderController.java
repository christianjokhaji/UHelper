package ca.unknown.bot.interface_interactor.schedule_reminder;

import ca.unknown.bot.use_cases.schedule_reminder.ScheduleInteractor;
import ca.unknown.bot.use_cases.schedule_reminder.ScheduledReminderInputData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class ScheduledReminderController {

    SlashCommandInteractionEvent event;
    ScheduleInteractor scheduleInteractor;

    public ScheduledReminderController(SlashCommandInteractionEvent event){
        this.event = event;
        //this.scheduleInteractor = scheduleInteractor;
    }

    public ScheduledReminderInputData execute(){
        int[] eventDate = new int[6];
        eventDate[0] = Objects.requireNonNull(event.getOption("year")).getAsInt();
        eventDate[1] = Objects.requireNonNull(event.getOption("month")).getAsInt();
        eventDate[2] = Objects.requireNonNull(event.getOption("day")).getAsInt();
        eventDate[3] = Objects.requireNonNull(event.getOption("hour")).getAsInt();
        eventDate[4] = Objects.requireNonNull(event.getOption("minute")).getAsInt();
        eventDate[5] = Objects.requireNonNull(event.getOption("sec")).getAsInt();

        if (event.getName().equals("schedule_event")) {
            String eventName = Objects.requireNonNull(event.getOption("eventName")).getAsString();
            ScheduledReminderInputData scheduledReminderInputData = new ScheduledReminderInputData(eventName, eventDate);
            return scheduledReminderInputData;
        }
        else if (event.getName().equals("schedule_exam")) {
            String courseCode = Objects.requireNonNull(event.getOption("courseCode")).getAsString();
            String location = Objects.requireNonNull(event.getOption("location")).getAsString();
            ScheduledReminderInputData scheduledReminderInputData = new ScheduledReminderInputData(courseCode, eventDate, location);
            //scheduleInteractor.execute(scheduledReminderInputData);
            return scheduledReminderInputData;
        }
        else if (event.getName().equals("schedule_assignment")){
            String courseCode = Objects.requireNonNull(event.getOption("courseCode")).getAsString();
            String assignmentName = Objects.requireNonNull(event.getOption("assignmentName")).getAsString();
            ScheduledReminderInputData scheduledReminderInputData = new ScheduledReminderInputData(assignmentName, courseCode, eventDate);
            scheduleInteractor.execute(scheduledReminderInputData);
            return scheduledReminderInputData;
        }
        return null;
    }
}
