package ca.unknown.bot.interface_adapter.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.data_access.timer.TimerDAO;
import com.google.gson.internal.LinkedTreeMap;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TimerController {
     /**
     * An intermediate class in the Interface Adapters layer. This class receives the inputs from
     * View (Discord) and converts to an appropriate datatype (Map) for TimerDAO to process.
     *
     * A typical map that maps a Discord user onto timer looks like this. (and this is how
     *  a map is represented in timer_repository.json.)
     *
     * Map<String, ArrayList<LinkedTreeMap>>
     * {userID1: <timer1, timer2>, userID2: <timer3>}
     */


    public static void convertTimerInput(String name, double workTime, double breakTime, Integer iteration
    , User user) {
     /**
     * createTimer creates a map that looks like the example above. The map contains the user id
     * (String) as a key and the Pomodoro instance they want to create as a value. It will be passed
     * onto the TimerDAO for checking duplicate user and Pomodoro instances.
     *
      * @param name : the name of the new timer instance
      * @param workTime : the duration of a work period (within an interval)
      * @param breakTime : the duration of a break period (within an interval)
      * @param iteration : the number of intervals for a timer instance
      * @param user : the Discord user who used /timer_create
     */
        Pomodoro newTimer = new Pomodoro(workTime, breakTime, iteration, name);
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(newTimer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(user.toString(), value);
        TimerDAO timerDAO = new TimerDAO();
        timerDAO.savePomodoro(userAndTimer, "timer_repository.json");
    }

    public static LinkedTreeMap converttoLTM(Pomodoro pomodoro){
     /**
     * Converts the information above the given Pomodoro instance into a well-formed LinkedTreeMap
     * to ensure data consistency in timer_repository.
     *
     * @param pomodoro: the new pomodoro instance that the user wants to save
     * @return timer: a LinkedTreeMap that comes from pomodoro
     */
        LinkedTreeMap timer = new LinkedTreeMap();
        timer.put("name", pomodoro.getName());
        LinkedTreeMap spec = new LinkedTreeMap();
        spec.put("breakTime", pomodoro.getBreakTime());
        spec.put("iteration", pomodoro.getIteration());
        spec.put("workTime", pomodoro.getWorkTime());
        timer.put("map", spec);
        return timer;
    }

//    public static boolean checkDuplicateTimer(String name, User user) {
//     /**
//     * One of the representation invariants of Pomodoro is that the name of an instance of should
//     * never be equal to other Pomodoro instances for a user. As such, this function will return
//     * true if name is a duplicate such that the view displays a message to change name.
//     *
//     */
//        TimerDAO timerDAO = new TimerDAO();
//        return timerDAO.checkDuplicate(name, user.toString(), "timer_repository.json");
//    }


}
