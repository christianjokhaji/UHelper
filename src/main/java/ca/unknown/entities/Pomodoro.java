package ca.unknown.entities;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro implements Preset {
    private final HashMap<String,Integer> map;
    private final String name;

    public Pomodoro(Integer workMinute, Integer breakMinute, Integer iteration, String name){
        map = new HashMap<>();
        this.map.put("workMinute", workMinute);
        this.map.put("breakMinute", breakMinute);
        this.map.put("iteration", iteration);
        this.name = name;
    }

    public void commenceTimer() {
        for (int i = 0; i != map.get("iteration"); i++){
            try {
                System.out.println("Iteration: " + i);
                this.commenceWork();
                Thread.sleep(minToMillisecond(map.get("workMinute")));
                this.commenceBreak();
                Thread.sleep(minToMillisecond(map.get("breakMinute")));
        }   catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Your timer session is expired.");
    }

    public void commenceWork() {
        Timer timerForWork = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Work session has started.");
                timerForWork.cancel();
            }
        };
        timerForWork.schedule(task,0, minToMillisecond(map.get("workMinute")));
    }

    public void commenceBreak() {
        Timer timerForBreak = new Timer();
        TimerTask workend = new TimerTask(){
            public void run(){
                System.out.println("The break has started.");
                timerForBreak.cancel();
            }
        };
        timerForBreak.schedule(workend,0, minToMillisecond(map.get("breakMinute")));
    }

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

    private static int minToMillisecond(Integer min){
        return min * 60000;
    }
}
