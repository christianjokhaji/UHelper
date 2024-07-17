package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class ScheduledAssignmentInteractor extends ScheduledEventInteractor {
    public ScheduledAssignmentInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public void execute(ScheduledReminderInputData scheduledReminderInputData, String user, SlashCommandInteractionEvent event) {
        ScheduledEvent schedAssignment = eventFactory.create(scheduledReminderInputData.getAssignmentCourseCode(),
                scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName());
        scheduleDAO.getSchedule(user).addEvent(schedAssignment);
        event.reply("You have scheduled the following event: \n"+ schedAssignment.toString()).queue();
    }
}
