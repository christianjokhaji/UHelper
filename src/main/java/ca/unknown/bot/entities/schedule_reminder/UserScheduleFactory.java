package ca.unknown.bot.entities.schedule_reminder;

/**
 * Concrete implementation of a <code>ScheduleFactory</code> which creates a new <code>UserSchedule</code>.
 */
public class UserScheduleFactory implements ScheduleFactory {
    /**
     *
     * @param username the username of the user of this schedule
     * @param userID the discord userID of the user of this schedule
     * @return a new <code>UserSchedule</code> object
     */
    public Schedule create(String username, long userID){
        return new UserSchedule(username, userID);
    }
}
