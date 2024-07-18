package ca.unknown.bot.interface_adapter.schedule_reminder;

import ca.unknown.bot.use_cases.schedule_reminder.ScheduledReminderInputData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class ScheduledReminderController {
    private SlashCommandInteractionEvent event;
    private ScheduledReminderInputData scheduledReminderInputData;

    public ScheduledReminderController(SlashCommandInteractionEvent event){
        this.event = event;
    }

    private void execute(){
        int[] eventDate = new int[6];
        eventDate[0] = Objects.requireNonNull(event.getOption("year")).getAsInt();
        eventDate[1] = Objects.requireNonNull(event.getOption("month")).getAsInt();
        eventDate[2] = Objects.requireNonNull(event.getOption("day")).getAsInt();
        eventDate[3] = Objects.requireNonNull(event.getOption("hour")).getAsInt();
        eventDate[4] = Objects.requireNonNull(event.getOption("minute")).getAsInt();
        eventDate[5] = Objects.requireNonNull(event.getOption("sec")).getAsInt();

        if (event.getName().equals("schedule_event")) {
            String eventName = Objects.requireNonNull(event.getOption("event")).getAsString();
            this.setScheduledReminderInputData(new ScheduledReminderInputData(eventName, eventDate));
        }
        else if (event.getName().equals("schedule_exam")) {
            String courseCode = Objects.requireNonNull(event.getOption("course")).getAsString();
            String location = Objects.requireNonNull(event.getOption("location")).getAsString();
            this.setScheduledReminderInputData(new ScheduledReminderInputData(courseCode, eventDate, location));
        }
        else if (event.getName().equals("schedule_assignment")){
            String courseCode = Objects.requireNonNull(event.getOption("course")).getAsString();
            String assignmentName = Objects.requireNonNull(event.getOption("assignment")).getAsString();
            this.setScheduledReminderInputData(new ScheduledReminderInputData(assignmentName, courseCode, eventDate));
        }
    }

    public ScheduledReminderInputData getScheduledReminderInputData(){
        execute();
        return scheduledReminderInputData;
    }

    private void setScheduledReminderInputData(ScheduledReminderInputData scheduledReminderInputData){
        this.scheduledReminderInputData = scheduledReminderInputData;
    }
}
