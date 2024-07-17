package ca.unknown.bot.interface_interactor;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
import ca.unknown.bot.entities.PomodoroFactory;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TimerController {

    /**
     * An intermediate class in the Interface Adapters layer. This class receives the inputs from
     * View (Discord) and converts to an appropriate datatype (Map) for TimerDAO to process.
     *
     * A typical map that maps a Discord user onto timer looks like this. (and this is how,
     * hopefullu, is represented in timer_repository.json.)
     *
     * Map<String, ArrayList<Pomodoro>>
     * {user1: <timer1, timer2>, user2: <timer3>}
     */

    public static void saveTimer(String name, double workTime, double breakTime, Integer iteration
    , User user){

    /**
     * createTimer creates a map that looks like the example above. The map contains the user id
     * (String) as a key and the Pomodoro instance they want to create as a value. It will be passed
     * onto the TimerDAO for checking duplicate user and Pomodoro instances.
     *
     */
        Pomodoro newTimer = PomodoroFactory.create(workTime, breakTime, iteration, name);
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(newTimer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(user.toString(), value);
        TimerDAO timerDAO = new TimerDAO();
        timerDAO.savePomodoro(userAndTimer, "timer_repository.json");

    }
}
