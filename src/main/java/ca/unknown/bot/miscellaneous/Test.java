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
        Pomodoro test = new Pomodoro(1, 1, 2, "test");
        test.commenceTimer();

            }
    }
