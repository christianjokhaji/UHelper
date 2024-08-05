package ca.unknown.bot.interface_adapter.schedule_reminder;

import ca.unknown.bot.use_cases.schedule_reminder.ScheduledReminderInputData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

/**
 * A controller for the scheduling reminders use case. Converts raw event input data into a new
 * <code>ScheduledReminderInputData</code> object for the DAO to use.
 */
public class ScheduledReminderController {
    /**
     * The slash command that was used during this interaction.
     */
    private SlashCommandInteractionEvent event;

    /**
     * The converted scheduled event input data.
     */
    private ScheduledReminderInputData scheduledReminderInputData;

    /**
     * Class constructor.
     * @param event the slash command that was used during this interaction
     */
    public ScheduledReminderController(SlashCommandInteractionEvent event){
        this.event = event;
    }

    /**
     * Collects raw input data about this event and feeds it into a new <code>ScheduledReminderInputData</code>
     * object to store.
     */
    private void execute(){
        // the event date is collected as an integer array from the user
        int[] eventDate = new int[6];
        eventDate[0] = Objects.requireNonNull(event.getOption("year")).getAsInt();
        eventDate[1] = Objects.requireNonNull(event.getOption("month")).getAsInt();
        eventDate[2] = Objects.requireNonNull(event.getOption("day")).getAsInt();
        eventDate[3] = Objects.requireNonNull(event.getOption("hour")).getAsInt();
        eventDate[4] = Objects.requireNonNull(event.getOption("minute")).getAsInt();
        eventDate[5] = 0; // sets the seconds value to 00

        if (event.getName().equals("schedule-event")) {
            String eventName = Objects.requireNonNull(event.getOption("event")).getAsString();

            // creates a new ScheduledReminderInputData object to store the data
            this.scheduledReminderInputData = new ScheduledReminderInputData(eventName, eventDate);
        }
        else if (event.getName().equals("schedule-exam")) {
            String courseCode = Objects.requireNonNull(event.getOption("course")).getAsString();
            String location = Objects.requireNonNull(event.getOption("location")).getAsString();

            // creates a new ScheduledReminderInputData object to store the data
            this.scheduledReminderInputData = new ScheduledReminderInputData(courseCode, eventDate, location);
        }
        else if (event.getName().equals("schedule-assignment")){
            String courseCode = Objects.requireNonNull(event.getOption("course")).getAsString();
            String assignmentName = Objects.requireNonNull(event.getOption("assignment")).getAsString();

            // creates a new ScheduledReminderInputData object to store the data
            this.scheduledReminderInputData = new ScheduledReminderInputData(assignmentName, courseCode, eventDate);
        }
    }

    /**
     * Returns a <code>ScheduledReminderInputData</code> object with newly formatted data for the DAO to use.
     * @return a new <code>ScheduledReminderInputData</code> object
     */
    public ScheduledReminderInputData getScheduledReminderInputData(){
        // runs the data conversion method which is private
        execute();

        return scheduledReminderInputData;
    }
}
