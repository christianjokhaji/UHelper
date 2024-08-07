package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.Schedule;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.JDA;

import java.util.Map;

public class RestartScheduleInteractor {
    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;

    private JDA jda;


    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public RestartScheduleInteractor(ScheduledReminderDataAccessInterface scheduleDAO, JDA jda){
        this.scheduleDAO = scheduleDAO;
        this.jda = jda;
    }

    public void execute(){
        Map<String, Schedule> repo = scheduleDAO.getRepo();


        for(String username: repo.keySet()){
            Schedule s = scheduleDAO.getSchedule(username);
            long userId = s.getUserId();

            scheduleDAO.addNewCheck(username);

            for(ScheduledEvent e: s.getEvents()){
                // adds the event back to the user's reminder alert list
                scheduleDAO.addExistingCheck(username, e.getEventName());

                // call subordinate interactor to set up a delayed queue for sending a private message reminder to the user
                jda.retrieveUserById(userId).queue(user -> new SendReminderInteractor(scheduleDAO).execute(user, e));


                // call subordinate interactor to clean up event from schedule after its passed
                new RemovePassedEventInteractor(scheduleDAO).execute(username, e);
            }
        }
    }
}
