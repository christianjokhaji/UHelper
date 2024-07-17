package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDAO;
import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.*;
import ca.unknown.bot.interface_interactor.schedule_reminder.ScheduledReminderController;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ScheduledReminderInteractor extends ListenerAdapter {
    /**
     *
     *
     * @param event represents a SlashCommandInteraction event.
     */
    ScheduledReminderDataAccessInterface scheduleDAO = new ScheduledReminderDAO();
    ScheduleFactory scheduleFactory = new UserScheduleFactory();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String user = event.getInteraction().getUser().getName();

        if (event.getName().equals("schedule_event")) {
            if(!scheduleDAO.existsByUser(user)){
                scheduleDAO.save(scheduleFactory.create(user));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();
            new ScheduledEventInteractor(scheduleDAO).execute(scheduledReminderInputData, user);

            event.reply(scheduleDAO.getSchedule(user).toString());
        }
        else if (event.getName().equals("schedule_exam")) {
            if(!scheduleDAO.existsByUser(user)){
                scheduleDAO.save(scheduleFactory.create(user));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();
            new ScheduledExamInteractor(scheduleDAO).execute(scheduledReminderInputData, user);

            event.reply(scheduleDAO.getSchedule(user).toString());
        }
        else if (event.getName().equals("schedule_assignment")){
            if(!scheduleDAO.existsByUser(user)){
                scheduleDAO.save(scheduleFactory.create(user));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();
            new ScheduledAssignmentInteractor(scheduleDAO).execute(scheduledReminderInputData, user);

            event.reply(scheduleDAO.getSchedule(user).toString());
        }
    }

}
