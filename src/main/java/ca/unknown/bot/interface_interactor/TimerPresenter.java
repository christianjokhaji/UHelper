package ca.unknown.bot.interface_interactor;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
import ca.unknown.bot.entities.PomodoroFactory;
import com.google.gson.internal.LinkedTreeMap;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimerPresenter {
    /**
     * An intermediate class in the Interface Adapters layer. This class receives the outputs from
     * inner layers and converts to an appropriate datatype for TimerInteractor to process.
     *
     * Pomodoro instances are stored in the following format as a json file.
     * {user_id: [{"name": "name_of_timer, "map": {"breakTime":5.0,"iteration":3,"workTime":25.0}}]}
     *
     * 1) user_id is a String object that comes from user.toString()
     * 2) user_id is mapped onto ArrayList. It contains the information about a Pomodoro instance,
     * which is retained as an instance of LinkedTreeMap (a custom data structure provided by GSON)
     * 3) A LinkedTreeMap has key-value pairs. In the example above, "map" corresponds to
     * another LinkedTreeMap.
     */

    public static String getTimers(User user) {
     /**
     * A presenter method that is used for /timer_list. This fetches an ArrayList of LinkedTreeMaps
     * and converts them into a Pomodoro instance and an appropriate message for the discord bot
     * to reply with.
     *
     */
        TimerDAO timerDAO = new TimerDAO();
        String message = new String("You have the following timer(s):" + "\n");
        ArrayList list = timerDAO.loadTimers(user.toString(), "timer_repository.json");
        for (int i = 0; i < list.size(); i++) {
            LinkedTreeMap treeMap = (LinkedTreeMap) list.get(i);
            String name = treeMap.get("name").toString();
            LinkedTreeMap spec = (LinkedTreeMap) treeMap.get("map");
            double workTime = (double) spec.get("workTime");
            double breakTime = (double) spec.get("breakTime");
            Double iteration = (Double) spec.get("iteration");
            Integer it = iteration.intValue();
            Pomodoro pomodoro = new Pomodoro(workTime,breakTime,it,name);
            message = message + pomodoro.toString() + "\n";
        }
        return message;
    }
}
