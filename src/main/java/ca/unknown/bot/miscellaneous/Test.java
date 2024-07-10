package ca.unknown.bot.miscellaneous;

import ca.unknown.bot.entities.Pomodoro;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    /**
     * A test class for whatever you want to test.
     *
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Pomodoro test = new Pomodoro(0.5, 0.5, 2, "test");
        test.commenceTimer();

//        long startTime = System.currentTimeMillis();
//        long endTime = startTime + 5000;
//        Timer checker = new Timer();
//        TimerTask task = new TimerTask() {
//            public void run() {
//                if (System.currentTimeMillis() > endTime) {
//                    System.out.println("Timer ended");
//                    checker.cancel();
//                }
//            }
//        };
//        checker.scheduleAtFixedRate(task, 200, 200);
            }
    }
