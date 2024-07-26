package ca.unknown.bot.app;

import ca.unknown.bot.entities.timer.Pomodoro;

public class TEST {
    public static void main(String[] args) {
        Pomodoro pomodoro = new Pomodoro(0.5, 0.08333333, 2, "test");
        pomodoro.startTimer();
    }
}
