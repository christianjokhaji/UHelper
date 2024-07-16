package ca.unknown.bot.entities;

public class UserScheduleFactory implements ScheduleFactory{
    public Schedule create(String user){
        return new UserSchedule(user);
    }
}
