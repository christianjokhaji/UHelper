package ca.unknown.bot.interface_adapter.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.data_access.timer.TimerDAO;
import ca.unknown.bot.use_cases.timer.TimerInteractor;
import com.google.gson.internal.LinkedTreeMap;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TimerController {
     /**
     * A controller class in the Interface Adapters layer. This class receives the inputs from
     * TimerListener and converts to an appropriate datatype (Map) for TimerInteractor to process.
     *
     * A typical map that maps a Discord user onto timer looks like this. (and this is how
     * a map is represented in timer_repository.json.)
     *
     * Map<String, ArrayList<LinkedTreeMap>>
     * {userID1: <timer1, timer2>, userID2: <timer3>}
     */


    public static void convertCreateInput(String name, double workTime, double breakTime, int iteration
    , User user, SlashCommandInteractionEvent event) {
     /**
     * convertCreateInput creates a map that looks like the example above. The map contains the user id
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
        TimerInteractor.timerCreate(userAndTimer, event);
    }

    public static void convertDeleteInput(String name, User user, SlashCommandInteractionEvent event) {
        TimerInteractor.timerDelete(name, user.toString(), event);
    }

    public static void convertStartInput(String name, User owner, User one, User two, User three,
                                         SlashCommandInteractionEvent event) {
        ArrayList<User> users = new ArrayList<>();
        users.add(owner);
        if (one != null) {users.add(one);}
        if (two != null) {users.add(two);}
        if (three != null) {users.add(three);}
        TimerInteractor.timerStart(name, users, event);
    }

    public static LinkedTreeMap convertToLTM(Pomodoro pomodoro){
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







}
