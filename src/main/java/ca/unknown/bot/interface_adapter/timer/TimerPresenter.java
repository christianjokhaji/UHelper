package ca.unknown.bot.interface_adapter.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.data_access.timer.TimerDAO;
import com.google.gson.internal.LinkedTreeMap;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

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
     * @param user : the user who requested to see their list of timers
     * @return message : the message that timerInteractor needs to pass onto a jda instance
     *
     */
        TimerDAO timerDAO = new TimerDAO();
        ArrayList list = timerDAO.loadTimers(user.toString(), "timer_repository.json");
        if (list == null || list.size() == 0) {return "You have no timer preset!";}
        String message = new String("You have the following timer(s):" + "\n\n");
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

    public static Pomodoro fetchTimer(String name, User user) {
    /**
     * fetchTimer is used when a Discord user requests to use a timer instance through /timer_start.
     *
     * @param name : the name of the requested timer
     * @param user : the Discord user who called /timer_start
     */
        TimerDAO timerDAO = new TimerDAO();
        ArrayList listTimers = timerDAO.loadTimers(user.toString(), "timer_repository.json");
        for (Object map : listTimers) {
            map = (LinkedTreeMap) map;
            if (((LinkedTreeMap<?, ?>) map).get("name").equals(name)) {
                Pomodoro timer = convertToPomodoro((LinkedTreeMap) map);
                return timer;
            }
        }
        return null;
    }

    private static Pomodoro convertToPomodoro(LinkedTreeMap timer) {
        String name = timer.get("name").toString();
        LinkedTreeMap spec = (LinkedTreeMap) timer.get("map");
        double breakTime = Double.parseDouble(spec.get("breakTime").toString());
        double workTime = Double.parseDouble(spec.get("workTime").toString());
        Double it = Double.parseDouble(spec.get("iteration").toString());
        Integer iteration = it.intValue();
        return new Pomodoro(workTime, breakTime, iteration, name);
    }
}
