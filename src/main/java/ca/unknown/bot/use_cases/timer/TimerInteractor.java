package ca.unknown.bot.use_cases.timer;

import ca.unknown.bot.data_access.timer.TimerDAO;
import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.interface_adapter.timer.TimerPresenter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Map;

public class TimerInteractor {
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

    public static void timerCreate(Map userAndTimer, SlashCommandInteractionEvent event) {
        String userId = userAndTimer.keySet().toArray()[0].toString();
        ArrayList timers = (ArrayList) userAndTimer.get(userId);
        Pomodoro timer = (Pomodoro) timers.get(0);
        String name = timer.getName();

        if (TimerDAO.checkDuplicate(name, userId)) { // Case 1
            TimerPresenter.sendReply(event, "Duplicate names are not allowed " +
                            "for timer instances. Try again with a different name.");}

        else if (TimerDAO.checkMoreThanFive(userId)) { // Case 2
            TimerPresenter.sendReply(event, "You have reached the maximum number of timers! " +
                    "Delete one before adding a new one.");}

        else if (TimerDAO.checkEmpty("timer_repository.json")) {
            TimerDAO.saveTimerOne(userAndTimer, "timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}

        else if (!TimerDAO.checkUser(userAndTimer, "timer_repository.json")) {
            TimerDAO.saveTimerTwo(userAndTimer, "timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}

        else if (TimerDAO.checkUser(userAndTimer, "timer_repository.json")) {
            TimerDAO.saveTimerThree(userAndTimer, "timer_repository.json");
            TimerPresenter.sendSuccessReply(event, "A timer preset has been created. " +
                    name + " will repeat " + timer.getWorkTime() + " minutes of work and " +
                    timer.getBreakTime() + " minutes of break " + timer.getIteration() + " times.");}

    }

    public static void timerDelete(String name, String user, SlashCommandInteractionEvent event) {
        if (TimerDAO.checkEmpty("timer_repository.json") ||
        !TimerDAO.checkUserDelete(user, "timer_repository.json")) {
            TimerPresenter.sendReply(event, "You don't have any presets to remove");}

        else if (TimerDAO.checkUserDelete(user, "timer_repository.json")) {
            TimerDAO.deletePomodoro(name, user, "timer_repository.json");
            TimerPresenter.sendSuccessReply(event, name + " has been successfully removed.");
        }
    }

    public static void timerStart(String name, ArrayList<User> users, SlashCommandInteractionEvent event) {

    }


}
