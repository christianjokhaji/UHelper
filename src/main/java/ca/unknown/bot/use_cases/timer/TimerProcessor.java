package ca.unknown.bot.use_cases.timer;

import ca.unknown.bot.data_access.timer.TimerDAO;
import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.interface_adapter.timer.TimerPresenter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Map;

public class TimerProcessor {
    /**
     * A use-case interactor class in the application business rule layer. This class receives the
     * inputs from TimerController and converts to an appropriate datatype (Map) for TimerDAO to process.
     *
     * A typical map that maps a Discord user onto timer looks like this. (and this is how
     * a map is represented in timer_repository.json.)
     *
     * Map<String, ArrayList<LinkedTreeMap>>
     * {userID1: <timer1, timer2>, userID2: <timer3>}
     */

     private static ArrayList<Pomodoro> timerList = new ArrayList<>();

    public static void timerCreate(Map userAndTimer, SlashCommandInteractionEvent event) {
    /**
     * timerCreate orders TimerDAO to check for these two conditions:
     * 1) whether there is a timer instance with the identical name
     * 2) if user has too many timers (5)
     *
     * After knowing that these two conditions are not violated, it will once again ask TimerDAO to
     * ask how timer_repository looks like, and depend on that, it will invoke different methods in
     * TimerDAO to store a timer instance.
     * Case 1) timer_repository is completely empty
     * Case 2) timer_repository doesn't contain the user
     * Case 3) timer_repository has the user, but no timer
     *
     * @param userAndTimer : a map containing user as the key and the timer instance as the value
     * @param event : the RestAction event, which will be passed to TimerPresenter
     */
        String userId = userAndTimer.keySet().toArray()[0].toString();
        ArrayList timers = (ArrayList) userAndTimer.get(userId);
        Pomodoro timer = (Pomodoro) timers.get(0);
        String name = timer.getName();

        // Denies the creation if there is another timer instance with the same name
        if (TimerDAO.checkDuplicate(name, userId)) {
            TimerPresenter.sendReply(event, "Duplicate names are not allowed " +
                            "for timer instances. Try again with a different name.");}

        // Denies the creation if the user has five or more timers already
        else if (TimerDAO.checkMoreThanFive(userId)) {
            TimerPresenter.sendReply(event, "You have reached the maximum number of timers! " +
                    "Delete one before adding a new one.");}

        else if (TimerDAO.checkEmpty("src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json")) { // Case 1
            TimerDAO.saveTimerOne(userAndTimer, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}

        else if (!TimerDAO.checkUser(userId, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json")) { // Case 2
            TimerDAO.saveTimerTwo(userAndTimer, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}

        else if (TimerDAO.checkUser(userId, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json")) { // Case 3
            TimerDAO.saveTimerThree(userAndTimer, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}
    }

    public static void timerDelete(String name, String user, SlashCommandInteractionEvent event) {
    /**
     * An interactor method for deleting timer. Uses similar logic as above.
     *
     * @param name : the name of the timer to be deleted from timer_repository
     * @param user : the user who requested the removal
     * @param event : the event to reply that either the requested timer preset doesn't exist or it
     *              is removed.
     */
        if (TimerDAO.checkEmpty("src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json") || !TimerDAO.checkUser(user, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json")) {
            TimerPresenter.sendReply(event, "You don't have any presets to remove.");}

        else if (!TimerDAO.checkTimer(user, name,"src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json")) {
            TimerPresenter.sendReply(event, "The requested timer is not found!");
        }

        else {
            TimerDAO.deletePomodoro(name, user, "src/main/java/ca/unknown/bot/data_access/timer/timer_repository.json");
            TimerPresenter.sendSuccessReply(event, name + " has been successfully removed.");
        }
    }


    public static void timerStart(String timerName, ArrayList<User> users, SlashCommandInteractionEvent event) {
        User owner = users.get(0);
        Pomodoro timer = TimerDAO.fetchTimer(timerName, owner.toString());
        if (timer == null) {TimerPresenter.sendReply(event, "The requested timer is not found");}
        else {
            if (checkTimerRunning(owner)) {
                TimerPresenter.sendReply(event, "There is a timer already running!");
            } else {
                for (User user : users) {
                    timer.addUser(user);}
                cleanTimerList();
                timer.startTimer();
                timerList.add(timer);
                TimerPresenter.sendReply(event, timerName + " has started.");
            }
        }
    }

    // Searches timerList to find the corresponding timer and unregister user
    public static void TimerCancel(String timerName, User user, SlashCommandInteractionEvent event) {
        for (Pomodoro timer : timerList) {
            if (timer.getName().equals(timerName)) {
                timer.removeUser(user);
                TimerPresenter.sendReply(event, "Timer successfully cancelled.");
                break;
            }
        }
        cleanTimerList();
        TimerPresenter.sendReply(event, "Timer is not found.");
    }

    // checks if user has a timer running in the background
    private static boolean checkTimerRunning(User user) {
        for (Pomodoro timer : timerList) {
            if (timer.containsUser(user)) {return true;}
        }
        return false;
    }

    // clear up timerList regularly if timers no longer have subscribers
    private static void cleanTimerList() {
        for (Pomodoro timer : timerList) {
            if (timer.getUsers().isEmpty()) {
                timerList.remove(timer);}
        }
    }



}
