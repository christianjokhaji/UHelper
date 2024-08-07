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
 * Primary Interactor for the scheduled reminder use case. It calls subordinate interactors to create events
 * and is tasked with setting up direct message reminders.
 */
public class ScheduledReminderInteractor extends ListenerAdapter {
    /**
     * The data access object in use for scheduling reminders.
     */
    ScheduledReminderDataAccessInterface scheduleDAO = new ScheduledReminderDAO();

    /**
     * The factory used to create new user schedules.
     */
    ScheduleFactory scheduleFactory = new UserScheduleFactory();


    /**
     * Performs the event interaction based on a slash command entered by the user.
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User discordUser = event.getUser();

        // collects the user's discord username and userID
        String username = discordUser.getName();

        long userId = discordUser.getIdLong();

         // loads the persisted repo onto the cache
        if(scheduleDAO.emptyCache("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json")){
            scheduleDAO.loadRepo("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
            new RestartScheduleInteractor(scheduleDAO, event.getJDA()).execute();
        }

        if (event.getName().equals("schedule-event")) {
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();

            // if the user is scheduling an event for the first time, make a new Schedule object for their use
            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            // calls a controller to receive user input and convert it into data the subordinate interactor can use
            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            // if the user tries to schedule an event in the past then stop execution
            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) < 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateEvent(scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getEventDate())){
                event.getHook().sendMessage("You have already scheduled this event!").complete();
                return;
            }

            // call subordinate interactor to schedule new generic event and return it
            ScheduledEvent newEvent = new ScheduledEventInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            // call subordinate interactor to set up a delayed queue for sending a private message reminder to the user
            new SendReminderInteractor(scheduleDAO).execute(discordUser, newEvent);

            // call subordinate interactor to clean up event from schedule after its passed
            new RemovePassedEventInteractor(scheduleDAO).execute(username, newEvent);
        }
        else if (event.getName().equals("schedule-exam")) {
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();

            // if the user is scheduling an event for the first time, make a new Schedule object for their use
            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            // calls a controller to receive user input and convert it into data the subordinate interactor can use
            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            // if the user tries to schedule an event in the past then stop execution
            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) <= 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateExam(scheduledReminderInputData.getEventDate())){
                event.getHook().sendMessage("You already have an exam scheduled at this time!").complete();
                return;
            }

            // call subordinate interactor to schedule new exam event and return it
            ScheduledEvent newExam = new ScheduledExamInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            // call subordinate interactor to set up a delayed queue for sending a private message reminder to the user
            new SendReminderInteractor(scheduleDAO).execute(discordUser, newExam);

            // call subordinate interactor to clean up event from schedule after its passed
            new RemovePassedEventInteractor(scheduleDAO).execute(username, newExam);

        }
        else if (event.getName().equals("schedule-assignment")){
            // Tell discord we received the command, send a thinking... message to the user
            event.deferReply().queue();

            // if the user is scheduling an event for the first time, make a new Schedule object for their use
            if(!scheduleDAO.existsByUser(username)){
                scheduleDAO.saveNewUser(scheduleFactory.create(username, userId));
            }

            // calls a controller to receive user input and convert it into data the subordinate interactor can use
            ScheduledReminderController scheduledReminderController = new ScheduledReminderController(event);
            ScheduledReminderInputData scheduledReminderInputData = scheduledReminderController.getScheduledReminderInputData();

            // if the user tries to schedule an event in the past then stop execution
            if(scheduledReminderInputData.getEventDate().compareTo(new Date()) < 0){
                event.getHook().sendMessage("You can only schedule a future event!").complete();
                return;
            }
            else if(scheduleDAO.getSchedule(username).hasDuplicateAssignment(scheduledReminderInputData.getEventName(),
                    scheduledReminderInputData.getAssignmentCourseCode())){
                event.getHook().sendMessage("You have already scheduled this assignment!").complete();
                return;
            }

            // call subordinate interactor to schedule new assignment event and return it
            ScheduledEvent newAssignment = new ScheduledAssignmentInteractor(scheduleDAO).execute(scheduledReminderInputData, username, event);

            // call subordinate interactor to set up a delayed queue for sending a private message reminder to the user
            new SendReminderInteractor(scheduleDAO).execute(discordUser, newAssignment);


            // call subordinate interactor to clean up event from schedule after its passed
            new RemovePassedEventInteractor(scheduleDAO).execute(username, newAssignment);


        }
        else if (event.getName().equals("current-schedule")){
            // if the user has never scheduled an event before (so their schedule doesn't exist in the cache) or
            // they have an existing schedule with no ongoing events, then alert the user that there are no events
            // to be displayed
            if(!scheduleDAO.existsByUser(username)){
                event.reply("You have no scheduled events.").queue();
            }
            else if(scheduleDAO.getSchedule(username).hasNoEvents()){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else{
                // otherwise displays a String representation of the user's ongoing event schedule
                scheduleDAO.getSchedule(username).sort();
                event.reply(scheduleDAO.getSchedule(username).toString()).queue();
            }
        }
        else if(event.getName().equals("clear-event")){
            // if the user doesn't have an existing schedule in the cache, then alert them that there is no schedule
            // to clear
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
                jda.addEventListener(new ClearEventListener(scheduleDAO.getSchedule(username).size(), scheduleDAO, jda));

            }
        }
        else if(event.getName().equals("clear-schedule")){
            // if the user doesn't have an existing schedule in the cache, then alert them that there is no schedule
            // to clear
            if(!scheduleDAO.existsByUser(username)){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else if(scheduleDAO.getSchedule(username).hasNoEvents()){
                event.reply("There is no ongoing schedule to clear.").queue();
            }
            else{
                // otherwise, use the DAO to access the user's schedule and clear it
                scheduleDAO.getSchedule(username).clearSched();

                // unsubscribe user from all reminder alerts
                scheduleDAO.removeAllChecks(username);

                // update repo with the new cache containing the cleared schedule
                scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");

                // alert the user of the successful event
                event.reply("Schedule cleared successfully. You are unsubscribed from any reminder alerts.").queue();
            }
        }
    }

}
