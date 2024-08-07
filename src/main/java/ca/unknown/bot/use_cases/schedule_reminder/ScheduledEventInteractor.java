package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEventFactory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * The base class use case interactor for scheduling a new event.
 */
public class ScheduledEventInteractor {
    /**
     * The factory used to create new scheduled events.
     */
    ScheduledEventFactory eventFactory = new ScheduledEventFactory();

    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;


    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public ScheduledEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    /**
     * Creates a new scheduled event using the event factory and uses the DAO to add it to the given user's
     * current schedule. Then it persists the updated cache to the schedule repository and sends a confirmation
     * message to the user with details about their newly scheduled event.
     * @param scheduledReminderInputData the formatted input data to create a new scheduled event
     * @param user the given user
     * @param event the current slash command event interaction
     * @return the new scheduled event
     */
    public ScheduledEvent execute(ScheduledReminderInputData scheduledReminderInputData, String user, SlashCommandInteractionEvent event){
        ScheduledEvent schedEvent = eventFactory.createEvent(scheduledReminderInputData.getEventDate(),
                scheduledReminderInputData.getEventName());
        scheduleDAO.getSchedule(user).addEvent(schedEvent);
        scheduleDAO.addExistingCheck(user, schedEvent.getEventName());
        scheduleDAO.saveToFile("src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json");
        event.getHook().sendMessage("You have scheduled the following event: \n"+ schedEvent.toString()).queue();
        return schedEvent;
    }
}
