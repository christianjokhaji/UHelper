package ca.unknown.bot.interface_adapter.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.use_cases.timer.TimerProcessor;
import com.google.gson.internal.LinkedTreeMap;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TimerController {
     /**
     * A controller class in the Interface Adapters layer. This class receives the inputs from
     * TimerInteractor and converts to an appropriate datatype for TimerProcessor to process.
     * How are the inputs like user information and timer settings processed? It depends on what
     * RestAction the caller (user) requested.
      *
      * @param event : the restaction event that the user requested
     */


    public static void convertCreateInput(String timerName, double workTime, double breakTime, int iteration
    , User user, SlashCommandInteractionEvent event) {
     /**
      * convertCreateInput creates a HashMap whose key is the user and value is the timer, covered in an
      * ArrayList. It will be passed onto the TimerInteractor for checking duplicate user and Pomodoro instances.
      *
      * @param timerName : the name of the new timer instance
      * @param workTime : the duration of a work period (within an interval)
      * @param breakTime : the duration of a break period (within an interval)
      * @param iteration : the number of intervals for a timer instance
      * @param user : the Discord user who used /timer_create
      */
        Pomodoro newTimer = new Pomodoro(workTime, breakTime, iteration, timerName);
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(newTimer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(user.toString(), value);
        TimerProcessor.timerCreate(userAndTimer, event);
    }

    public static void convertDeleteInput(String timerName, User user, SlashCommandInteractionEvent event) {
    /**
      * convertCreateInput creates a HashMap whose key is the user and value is the timer, covered in an
      * ArrayList. It will be passed onto the TimerInteractor for checking duplicate user and Pomodoro instances.
      *
      * @param timerName : the name of the new timer instance
      * @param user : the Discord user who called timer_cancel
      * @oaram event : the event, which the JDA instance requires to make a proper reply message
      */
        TimerProcessor.timerDelete(timerName, user.toString(), event);
    }

    public static void convertStartInput(String timerName, User owner, User one, User two, User three,
                                         SlashCommandInteractionEvent event) {
    /**
      * convertStartInput is a controller method for /timer_start. It adds the owner & and any
      * user that was given unless they are identical to the owner.
      *
      * @param timerName : the name of the new timer instance
      * @param user : the Discord user who called timer_start
      * @param one : the first user to be invited to timerName timer; null if not given
      * @param two : ""
      * @param three : ""
      * @oaram event : the event, which the JDA instance requires to make a proper reply message
      */
        ArrayList<User> users = new ArrayList<>();
        users.add(owner);
        if (one != null && one != owner) {users.add(one);}
        if (two != null && two != owner) {users.add(two);}
        if (three != null && three != owner) {users.add(three);}
        TimerProcessor.timerStart(timerName, users, event);
    }

    public static void convertCancelInput(String timerName, User user, SlashCommandInteractionEvent event) {
    /**
      * convertCancelInput converts inputs appropriate for the purpose of cancelling a timer.
      *
      * @param timerName : the name of the new timer instance
      * @param user : the Discord user who called timer_cancel
      * @oaram event : the event, which the JDA instance requires to make a proper reply message
      */
        TimerProcessor.TimerCancel(timerName, user, event);
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
