package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.Schedule;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.JDA;

import java.util.*;

/**
 * A subordinate use case interactor which restarts the schedule repository once the program is booted back up.
 */
public class RestartScheduleInteractor {
    /**
     * The current DAO in use by the program.
     */
    final ScheduledReminderDataAccessInterface scheduleDAO;

    private final JDA jda;


    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     * @param jda the current JDA instance
     */
    public RestartScheduleInteractor(ScheduledReminderDataAccessInterface scheduleDAO, JDA jda){
        this.scheduleDAO = scheduleDAO;
        this.jda = jda;
    }

    /**
     * Checks for which events have passed and removes them, and resubscribes each user to their reminder alerts.
     */
    public void execute(){
        Map<String, Schedule> repo = scheduleDAO.getRepo();

        for(String username: repo.keySet()){
            ArrayList<ScheduledEvent> passedEvents = new ArrayList<>();

            Schedule s = scheduleDAO.getSchedule(username);
            long userId = s.getUserId();

            scheduleDAO.addNewCheck(username);

            for(ScheduledEvent e: s.getEvents()){
                if(e.getEventDate().compareTo(new Date()) < 0){
                    passedEvents.add(e);
                }
                else {
                    // adds the event back to the user's reminder alert list
                    scheduleDAO.addExistingCheck(username, e.getEventName());

                    // call subordinate interactor to set up a delayed queue for sending a private message reminder to the user
                    jda.retrieveUserById(userId).queue(user -> new SendReminderInteractor(scheduleDAO).execute(user, e));


                    // call subordinate interactor to clean up event from schedule after its passed
                    new RemovePassedEventInteractor(scheduleDAO).execute(username, e);
                }
            }
            this.removePassedEvents(s, passedEvents);
        }
    }

    private void removePassedEvents(Schedule s, List<ScheduledEvent> passedEvents){
        for(ScheduledEvent e: passedEvents){
            s.removePassedEvent(e);
        }
    }
}
