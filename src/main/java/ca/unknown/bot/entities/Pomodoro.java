package ca.unknown.bot.entities;

import java.util.*;
import java.lang.Math;

public class Pomodoro implements Preset {
    private final HashMap<String, Object> map;
    private final String name;

     /**
     * Pomodoro is a representation of timer preset that discord users can configure with how long
     * their study time and break time are.
     * <p>
     * Representation Invariants:
     * 1) workMinute and breakMinute should be a positive rational number, while iteration
     * should only be a positive integer.
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
        map = new HashMap<>();
        this.map.put("workTime", workTime);
        this.map.put("breakTime", breakTime);
        this.map.put("iteration", iteration);
        this.name = name;
    }

    /**
      * Starts a Pomodoro instance
      * It has two helper methods: commenceWork and commenceBreak, each of which starts own
      * respective timer.
      *
      */
    public void commenceTimer() {
        for (int i = 0; i != ((Integer) map.get("iteration")); i++){
            try {
                // Prints out what nth interval the timer is on
                System.out.println("Iteration: " + i);

                // fetches the current time from System in milliseconds
                long currentTime = System.currentTimeMillis();
                // calculates the time to end a session by adding the input from user
                long endTime = currentTime + minToMilli((double) map.get("workTime"));
                System.out.println("Work period has started at " + new Date());
                this.commenceWork(endTime);
                Thread.sleep(minToMilli((double) map.get("workTime")));

                // updates currentTime and endTime
                currentTime = System.currentTimeMillis();
                endTime = currentTime + minToMilli((double) map.get("breakTime"));
                System.out.println("Break period has started at " + new Date());
                this.commenceBreak(endTime);
                Thread.sleep(minToMilli((double) map.get("breakTime")));
            }
            // Do not delete this; essential for using Thread.sleep()
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Your timer has ended. Use /timer_create or /timer_start to start " +
                "another " + "timer.");
    }

    // Helper function for starting a work session
    private void commenceWork(long endTime) {
        Timer timerForWork = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    System.out.println("Work period has ended at " + new Date());
                    timerForWork.cancel();
                }
            }
        }; // Checks whether the desired time passed for every 0.1 seconds.
        timerForWork.scheduleAtFixedRate(task, 100, 100);
    }

    // Helper function for starting a break session
    private void commenceBreak(long endTime) {
        Timer timerForBreak = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (System.currentTimeMillis() >= endTime) {
                    System.out.println("Break period has ended at " + new Date());
                    timerForBreak.cancel();
                }
            }
        };
        timerForBreak.scheduleAtFixedRate(task, 100, 100);
    }

    // To be implemented
    public void cancel() {
        return;
    }

    // Getters for Pomodoro
    @Override
    public int getWorkTime() {
        return (int) this.map.get("workTime");
    }

    @Override
    public int getBreakTime() {
        return (int) this.map.get("breakTime");
    }

    @Override
    public int getIteration() {
        return (int) this.map.get("iteration");
    }

    @Override
    public String getName() {
        return this.name;
    }

    // A string representation of the Pomodoro class
    @Override
    public String toString() {
        return "A timer preset has been created. " + this.name + " will repeat " +
                map.get("workTime") + " minutes of work and " + map.get("breakTime") +
                " minutes of break " + map.get("iteration") + " times.";
    }

    // A helper function for converting minute to milliseconds
    private static long minToMilli(double min){
        return Math.round(min * 60 * 1000);
    }
}
