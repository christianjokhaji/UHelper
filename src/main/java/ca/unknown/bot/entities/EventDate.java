package ca.unknown.bot.entities;
import java.util.Calendar;

public class EventDate {
    private Calendar.Builder date = new Calendar.Builder();

        public EventDate(int year, int month, int day, int hour, int min, int sec){
            date.setDate(year, month - 1, day);
            date.setTimeOfDay(hour, min, sec);
        }

        public Calendar getDate(){
            return date.build();
        }
}
