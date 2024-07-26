package ca.unknown.bot.app;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private static final int WORK_TIME = 25 * 60 * 1000; // 25 minutes
    private static final int SHORT_BREAK_TIME = 5 * 60 * 1000; // 5 minutes
    private static final int LONG_BREAK_TIME = 15 * 60 * 1000; // 15 minutes
    private static int pomodoroCount = 0;

    private static Timer timer;
    private static TimerTask task;

    private static ArrayList<User> users;

    public PomodoroTimer() {
        users = new ArrayList<>();
    }

    public static void main(String[] args) {
        PomodoroTimer pomodoroTimer = new PomodoroTimer();
        // Adding sample users
        pomodoroTimer.addUser(new User("Alice"));
        pomodoroTimer.addUser(new User("Bob"));
        pomodoroTimer.startPomodoro();
    }

    public void addUser(User user) {
        users.add(user);
    }

    private static void startPomodoro() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                notifyUsers("Pomodoro session ended. Time for a break!");
                pomodoroCount++;
                if (pomodoroCount % 4 == 0) {
                    startBreak(LONG_BREAK_TIME);
                } else {
                    startBreak(SHORT_BREAK_TIME);
                }
            }
        };
        System.out.println("Starting Pomodoro session...");
        notifyUsers("Starting Pomodoro session...");
        timer.schedule(task, WORK_TIME);
    }

    private static void startBreak(int breakTime) {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                notifyUsers("Break ended. Time to start a new Pomodoro session!");
                startPomodoro();
            }
        };
        System.out.println("Starting break for " + (breakTime / (60 * 1000)) + " minutes...");
        notifyUsers("Starting break for " + (breakTime / (60 * 1000)) + " minutes...");
        timer.schedule(task, breakTime);
    }

    private static void notifyUsers(String message) {
        for (User user : users) {
            user.sendMessage(message);
        }
    }
}
