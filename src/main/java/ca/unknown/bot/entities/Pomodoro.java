package ca.unknown.bot.entities;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro implements Preset {
    private final HashMap<String,Integer> map;
    private final String name;

    /**
     * Pomodoro is a representation of timer preset that discord users can configure with how long
     * their study time and break time are.
     *
     * Representation Invariants:
     * 1) workMinute, breakMinute, and iteration must be a positive integer.
     * 2) name should never be equal to other Pomodoro instances.
     *
     * @param workMinute: the length of a study session in a timer preset
     * @param breakMinute: the length of a break session in a timer preset
     * @param iteration: how many times the user wants the study-break cycle to repeat
     * @param name: the name of a timer preset, which the user will refer to when calling
     */

    // The constructor of the Pomodoro class
    public Pomodoro(Integer workMinute, Integer breakMinute, Integer iteration, String name){
        map = new HashMap<>();
        this.map.put("workMinute", workMinute);
        this.map.put("breakMinute", breakMinute);
        this.map.put("iteration", iteration);
        this.name = name;
    }

    // The method that starts a timer session.
    public void commenceTimer() {
        for (int i = 0; i != map.get("iteration"); i++){
            try {
                System.out.println("Iteration: " + i);
                this.commenceWork();
                Thread.sleep(minToMillisecond(map.get("workMinute") - 1));
                this.commenceBreak();
                Thread.sleep(minToMillisecond(map.get("breakMinute") - 1));
        }   catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Your timer session is expired.");
    }

    // To be implemented
    public void cancel() {
        return;
    }

    // Helper Function for starting a work session
    private void commenceWork() {
        Timer timerForWork = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Work session has started.");
                timerForWork.cancel();
            }
        };
        timerForWork.schedule(task,0, minToMillisecond(map.get("workMinute")));
    }

    // Helper function for starting a break
    private void commenceBreak() {
        Timer timerForBreak = new Timer();
        TimerTask workend = new TimerTask(){
            public void run(){
                System.out.println("The break has started.");
                timerForBreak.cancel();
            }
        };
        timerForBreak.schedule(workend,0, minToMillisecond(map.get("breakMinute")));
    }

    // Getters for Pomodoro
    @Override
    public int getWorkMinute() {
        return this.map.get("workMinute");
    }

    @Override
    public int getBreakMinute() {
        return this.map.get("breakMinute");
    }

    @Override
    public int getIteration() {
        return this.map.get("iteration");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "A timer preset has been created. " + this.name + " repeats " +
                map.get("workMinute") + " minutes of work and " + map.get("breakMinute") +
                " minutes of break for " + map.get("iteration") + " times.";
    }

    // a helper function for converting minute to milliseconds
    private static int minToMillisecond(Integer min){
        return min * 60000;
    }
}
