package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDAO;
import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.*;
import ca.unknown.bot.interface_interactor.schedule_reminder.ScheduledReminderController;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ScheduleInteractor extends ListenerAdapter {
    /**
     *
     *
     * @param event represents a SlashCommandInteraction event.
     */
    ScheduledReminderDataAccessInterface scheduleDAO = new ScheduledReminderDAO();
    ScheduleFactory scheduleFactory = new UserScheduleFactory();

    ScheduledEventFactory eventFactory = new ScheduledEventFactory();
    private Schedule userSchedule;
    private ScheduledReminderInputData scheduledReminderInputData;

    public void execute(ScheduledReminderInputData scheduledReminderInputData){
        this.scheduledReminderInputData = scheduledReminderInputData;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ScheduledReminderController controller = new ScheduledReminderController(event);
        scheduledReminderInputData = controller.execute();

        String user = event.getInteraction().getUser().getName();

        if(scheduleDAO.existsByUser(user)){
            this.userSchedule = scheduleDAO.getSchedule(user);
        }
        else {
            this.userSchedule = scheduleFactory.create(user);
        }

        if (event.getName().equals("schedule_event")) {
            ScheduledEvent schedEvent = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName());
            userSchedule.addEvent(schedEvent);
            event.reply(schedEvent.reminderAlert());
        }
        else if (event.getName().equals("schedule_exam")) {
            ScheduledEvent exam = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getLocation());
            userSchedule.addEvent(exam);
            event.reply(exam.reminderAlert());
        }
        else if (event.getName().equals("schedule_assignment")){
            ScheduledEvent assignment = eventFactory.create(scheduledReminderInputData.getEventDate(), scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getLocation());
            userSchedule.addEvent(assignment);
            event.reply(assignment.reminderAlert());
        }
    }
}
