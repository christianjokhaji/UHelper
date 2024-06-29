package ca.unknown.entities;

import ca.unknown.entities.Pomodoro;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {
        Pomodoro test = new Pomodoro(3, 1, 3, "lol");
        test.commenceTimer();

    }


}
