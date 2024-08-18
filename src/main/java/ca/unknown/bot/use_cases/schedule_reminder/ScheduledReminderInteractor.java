package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDAO;
import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.*;
import ca.unknown.bot.interface_adapter.schedule_reminder.ScheduledReminderController;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

/**
 * Primary Interactor for the scheduled reminder use case. It calls subordinate interactors to create events, send
 * delayed direct message reminders, and remove events once they have passed.
 */
public class ScheduledReminderInteractor extends ListenerAdapter {
    /**
     * The data access object in use for scheduling reminders.
     */
    final ScheduledReminderDataAccessInterface scheduleDAO = new ScheduledReminderDAO();

    /**
     * The factory used to create new user schedules.
     */
    final ScheduleFactory scheduleFactory = new UserScheduleFactory();


    /**
     * Performs the event interaction based on a slash command entered by the user.
     * Upon scheduling an event:
     * Loads the persisted repo into the cache upon restarting the program, and then checks to see
     * if the user is scheduling an event for the first time, creating a new empty schedule for them if
     * necessary. If the user tries to schedule an event in the past, it will prevent them from doing so.
     * Subordinate interactors are called to schedule new events, send direct message reminders, and remove
     * events once they have passed, adhering to the single responsibility principle.
     * Upon trying to clear events:
     * Checks if the user has an ongoing schedule to clear or if the specified event is actually in the
     * schedule, preventing the user from proceeding if it is not. If the check passes, then the event(s) will be
     * removed from the user's schedule, and they are unsubscribed from the corresponding reminder alert(s).
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User discordUser = event.getUser();

        String username = discordUser.getName();

        long userId = discordUser.getIdLong();

        if(scheduleDAO.emptyCache("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json")){
            scheduleDAO.loadRepo("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
            new RestartScheduleInteractor(scheduleDAO, event.getJDA()).execute();
            scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
        }

        if (event.getName().equals("schedule-event")) {
            event.deferReply().queue();

            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) < 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateEvent(scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getEventDate())){
                event.getHook().sendMessage("You have already scheduled this event!").complete();
                return;
            }

            ScheduledEvent newEvent = new ScheduledEventInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            new SendReminderInteractor(scheduleDAO).execute(discordUser, newEvent);

            new RemovePassedEventInteractor(scheduleDAO).execute(username, newEvent);
        }
        else if (event.getName().equals("schedule-exam")) {
            event.deferReply().queue();

            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) <= 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateExam(scheduledReminderInputData.getEventDate())){
                event.getHook().sendMessage("You already have an exam scheduled at this time!").complete();
                return;
            }

            ScheduledEvent newExam = new ScheduledExamInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            new SendReminderInteractor(scheduleDAO).execute(discordUser, newExam);

            new RemovePassedEventInteractor(scheduleDAO).execute(username, newExam);

        }
        else if (event.getName().equals("schedule-assignment")){
            event.deferReply().queue();

            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) < 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateAssignment(scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getAssignmentCourseCode())){
                event.getHook().sendMessage("You have already scheduled this assignment!").complete();
                return;
            }

            ScheduledEvent newAssignment = new ScheduledAssignmentInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            new SendReminderInteractor(scheduleDAO).execute(discordUser, newAssignment);

            new RemovePassedEventInteractor(scheduleDAO).execute(username, newAssignment);


        }
        else if (event.getName().equals("current-schedule")){
            if(!scheduleDAO.existsByUser(username)){
                event.reply("You have no scheduled events.").queue();
            }
            else if(scheduleDAO.getSchedule(username).hasNoEvents()){
                event.reply("You have no scheduled events.").queue();
            }
            else{
                scheduleDAO.getSchedule(username).sort();
                event.reply(scheduleDAO.getSchedule(username).toString()).queue();
            }
        }
        else if(event.getName().equals("clear-event")){
            if(!scheduleDAO.existsByUser(username)){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else if(scheduleDAO.getSchedule(username).hasNoEvents()){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else{
                event.deferReply().queue();

                scheduleDAO.getSchedule(username).sort();
                event.getHook().sendMessage(scheduleDAO.getSchedule(username).toString()).queue();
                event.getHook().sendMessage("\nPlease reply with the index of the event you wish to clear.").queue();

                JDA jda = event.getJDA();
                jda.addEventListener(new ClearEventListener(scheduleDAO.getSchedule(username).getSize(), scheduleDAO, jda));

            }
        }
        else if(event.getName().equals("clear-schedule")){
            if(!scheduleDAO.existsByUser(username)){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else if(scheduleDAO.getSchedule(username).hasNoEvents()){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else{
                scheduleDAO.getSchedule(username).clearSched();

                scheduleDAO.removeAllChecks(username);

                scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");

                event.reply("Schedule cleared successfully. You are unsubscribed from any reminder alerts.").queue();
            }
        }
    }

}
