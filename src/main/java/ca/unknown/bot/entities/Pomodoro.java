package ca.unknown.bot.entities;

import java.util.*;

import java.lang.Math;

public class Pomodoro implements Preset {
    private final HashMap<String, Object> map;
    private final String name;

    /**
     * Pomodoro is a representation of timer preset that discord users can configure with how long
     * their study time and break time are.
     *
     * Representation Invariants:
     * 1) workMinute, breakMinute, and iteration must be a positive real number.
     * 2) name should never be equal to other Pomodoro instances.
     *
     * @param workTime: the length of a study session in a timer preset
     * @param breakTime: the length of a break session in a timer preset
     * @param iteration: how many times the user wants the study-break cycle to repeat
     * @param name: the name of a timer preset, which the user will refer to when calling
     */

    // The constructor of the Pomodoro class
    public Pomodoro(double workTime, double breakTime, Integer iteration, String name){
        map = new HashMap<>();
        this.map.put("workTime", workTime);
        this.map.put("breakTime", breakTime);
        this.map.put("iteration", iteration);
        this.name = name;
    }

    // The method that starts a timer session.
    public void commenceTimer() {
        for (int i = 0; i != ((Integer) map.get("iteration")); i++){
            try {
                System.out.println("Iteration: " + i);

                long currentTime = System.currentTimeMillis();
                long endTime = currentTime + minToMilli((double) map.get("workTime"));
                System.out.println("Work period has started at " + new Date());
                this.commenceWork(endTime);
                Thread.sleep(minToMilli((double) map.get("workTime")));

                currentTime = System.currentTimeMillis();
                endTime = currentTime + minToMilli((double) map.get("breakTime"));
                System.out.println("Break period has started at " + new Date());
                this.commenceBreak(endTime);
                Thread.sleep(minToMilli((double) map.get("breakTime")));
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Your timer is over. Use /timer_create or /timer_start to start another" +
                "timer");

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
        };
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
    public int getWorkMinute() {
        return (int) this.map.get("workTime");
    }

    @Override
    public int getBreakMinute() {
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

    @Override
    public String toString() {
        return "A timer preset has been created. " + this.name + " will repeat " +
                map.get("workTime") + " minutes of work and " + map.get("breakTime") +
                " minutes of break " + map.get("iteration") + " times.";
    }

    // a helper function for converting minute to milliseconds
    private static long minToMilli(double min){
        return Math.round(min * 60000);
    }
}
