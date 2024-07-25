package ca.unknown.bot.entities.timer;

import java.util.*;
import java.lang.Math;

public class Pomodoro implements TimerInterface {
    private final String name;
    private final HashMap<String, Object> map;

     /**
     * Pomodoro is a representation of timer preset that discord users can configure with how long
     * their study time and break time are.
     * <p>
     * Representation Invariants:
     * 1) workMinute and breakMinute should be a positive rational number, while iteration
     * should only be a positive integer larger than 0.
     * 2) name should never be equal to other Pomodoro instances.
     * <p>
     * Fun Fact: One unit of work-break (interval) is called a pomodoro, which means tomato
     * in Italian.
     *
     * @param workTime: the length of a study session in a timer preset
     * @param breakTime: the length of a break session in a timer preset
     * @param iteration: how many times the user wants the study-break cycle to repeat
     * @param name: the name of a timer preset, which the user will refer to when calling
        */

    /**
      * The Pomodoro constructor method.
      * Note that workTime, breakTime, and iteration are all stored in a Hashmap
      */
    public Pomodoro(double workTime, double breakTime, Integer iteration, String name){
        this.name = name;
        map = new HashMap<>();
        this.map.put("workTime", workTime);
        this.map.put("breakTime", breakTime);
        this.map.put("iteration", iteration);
    }

    /**
      * Starts a Pomodoro instance
      * It has two helper methods: commenceWork and commenceBreak, each of which starts own
      * respective timer.
     *
     * This version is to test the logic of Pomodoro on Console. It will not be used when Discord
     * needs it to serve the timer feature.
      *
      */
    public void startTimer() {
        for (int i = 0; i != ((Integer) map.get("iteration")); i++){
            try {
                // fetches the current time from System in milliseconds
                long currentTime = System.currentTimeMillis();
                // calculates the time to end a session by adding the input from user
                long endTime = currentTime + minToMilli((double) map.get("workTime"));
                this.startWork(endTime, i);
                Thread.sleep(minToMilli((double) map.get("workTime")));

                // updates currentTime and endTime
                currentTime = System.currentTimeMillis();
                endTime = currentTime + minToMilli((double) map.get("breakTime"));
                this.startBreak(endTime, i);
                Thread.sleep(minToMilli((double) map.get("breakTime")));
            }
            // Do not delete this; essential for using Thread.sleep()
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messagePrinter("Your timer has ended at "+ new Date() + "\nUse /timer_create or " +
                "/timer_start to start another timer.");
    }

    // Idea: make a helper function for
    private String messagePrinter(String str) {
        System.out.println(str);
        return str;
    }

    // Helper function for starting a work session
    private void startWork(long endTime, int i) {
        Timer timerForWork = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    timerForWork.cancel();
                }
            }
        };
        messagePrinter("Work period has started at " + new Date() + " (interval: " + (i+1) + ")");
        // Checks whether the desired time passed for every 0.1 seconds.
        timerForWork.scheduleAtFixedRate(task, 100, 100);
    }

    // Helper function for starting a break session
    private void startBreak(long endTime, int i) {
        Timer timerForBreak = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    timerForBreak.cancel();
                }
            }
        };
        messagePrinter("Break period has started at " + new Date() + " (interval: " + (i+1) + ")");
        timerForBreak.scheduleAtFixedRate(task, 100, 100);
    }

    // To be implemented
    public void cancel() {
        return;
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

    // A helper function for converting minute to millisecond
    private static long minToMilli(double min){
        return Math.round(min * 60 * 1000);
    }
}
