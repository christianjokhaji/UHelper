package ca.unknown.bot.entities.timer;

import net.dv8tion.jda.api.entities.User;

import java.util.*;
import java.lang.Math;

public class Pomodoro implements TimerInterface {

    private final String name;
    private final HashMap<String, Object> map;
    private final ArrayList<User> users;
    private int completedCycle;

    /**
     * Pomodoro is a representation of timer preset that Discord users can configure with how long
     * their study time and break time are in minutes.
     * <p>
     * Representation Invariants:
     * 1) workMinute and breakMinute should be a positive rational number, while iteration
     * should only be a positive integer larger than 0.
     * 2) name should never be equal to other Pomodoro instances.
     * <p>
     * Fun Fact: A cycle of work-break is called a pomodoro, which means tomato in Italian.
     * Responsibility: mimic the logic of real Pomodoro timer and sends a dm to the users registered
     *
     * @param workTime: the length of a study session in a timer preset
     * @param breakTime: the length of a break session in a timer preset
     * @param iteration: how many times the user wants the study-break cycle to repeat
     * @param name: the name of a timer preset, which the user will refer to when calling
        */

    public Pomodoro(double workTime, double breakTime, int iteration, String name){
    /**
      * The Pomodoro constructor method.
      * Note that workTime, breakTime, and iteration are all stored in a Hashmap.
      */
        this.name = name;
        map = new HashMap<>();
        this.map.put("workTime", workTime);
        this.map.put("breakTime", breakTime);
        this.map.put("iteration", iteration);
        this.users = new ArrayList<>();
        this.completedCycle = 0;
    }

    public void startTimer() {
    /**
      * Starts a Pomodoro instance.
      * It has two helper methods: commenceWork and commenceBreak, each of which starts own
      * respective timer.
      *
      */
        int totalCycle = getIteration();
        if (completedCycle < totalCycle) {
            long endTime = System.currentTimeMillis() + minToMilli(getWorkTime());
            startWork(endTime, completedCycle);
        }
    }

    // Helper function for starting a work session
    private void startWork(long endTime, int i) {
        Timer timerForWork = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    long endTime = System.currentTimeMillis() + minToMilli(getBreakTime());
                    startBreak(endTime, i);
                    timerForWork.cancel();
                }
            }
        };
        notifyUsers("Work period has started at " + new Date() + " (cycle: " + (i+1) + ")\n");
        timerForWork.scheduleAtFixedRate(task, 100, 100);
    }

    // Helper function for starting a break session
    private void startBreak(long endTime, int i) {
        Timer timerForBreak = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    completedCycle++;
                    long endTime = System.currentTimeMillis() + minToMilli(getWorkTime());
                    if (completedCycle < getIteration()) {startWork(endTime, completedCycle);}
                    else {notifyUsers("Your timer ended at " + new Date());}
                    timerForBreak.cancel();
                }
            }
        };
        notifyUsers("Break period has started at " + new Date() + " (cycle: " + (i+1) + ")\n");
        timerForBreak.scheduleAtFixedRate(task, 100, 100);
    }


    // Getters for Pomodoro
    @Override
    public double getWorkTime() {
        return (double) this.map.get("workTime");
    }

    @Override
    public double getBreakTime() {
        return (double) this.map.get("breakTime");
    }

    @Override
    public int getIteration() {
        return (int) this.map.get("iteration");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HashMap getMap() {return this.map;}

    // A string representation of the Pomodoro class
    @Override
    public String toString() {
        return  this.name + " - " +
                map.get("workTime") + " minutes of work, " + map.get("breakTime") +
                " minutes of break for " + map.get("iteration") + " time(s)";
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    private void notifyUsers(String message) {
        for (User user : users) {
            user.openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
        }
    }

    // A helper function for converting minute to millisecond
    private static long minToMilli(double min){
        return Math.round(min * 60 * 1000);
    }
}
