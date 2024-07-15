package ca.unknown.bot.entities;
import java.util.Calendar;

public class EventDate implements Comparable<EventDate> {
    private Calendar.Builder date = new Calendar.Builder();

        public EventDate(int year, int month, int day, int hour, int min, int sec){
            date.setDate(year, month, day);
            date.setTimeOfDay(hour, min, sec);
        }

        public Calendar getDate(){
            return date.build();
        }

        @Override
        public int compareTo(EventDate e){
            return getDate().compareTo(e.getDate());
        }
}
