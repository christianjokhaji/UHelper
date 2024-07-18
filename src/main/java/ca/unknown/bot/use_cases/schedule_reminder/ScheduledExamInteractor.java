package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ScheduledExamInteractor extends ScheduledEventInteractor {
    public ScheduledExamInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        super(scheduleDAO);
    }

    @Override
    public void execute(ScheduledReminderInputData scheduledReminderInputData, String user, SlashCommandInteractionEvent event) {
        ScheduledEvent schedExam = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName(),
                scheduledReminderInputData.getLocation());
        scheduleDAO.getSchedule(user).addEvent(schedExam);
        event.reply("You have scheduled the following event: \n"+ schedExam.toString()).queue();
    }
}
