package ca.unknown.bot.entities.schedule_reminder;

/**
 * Concrete implementation of a <code>ScheduleFactory</code> which creates a new <code>UserSchedule</code>.
 */
public class UserScheduleFactory implements ScheduleFactory {
    /**
     *
     * @param user the user of this schedule
     * @return a new <code>UserSchedule</code> object
     */
    public Schedule create(String user){
        return new UserSchedule(user);
    }
}
