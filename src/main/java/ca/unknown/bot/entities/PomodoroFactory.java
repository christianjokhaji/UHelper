package ca.unknown.bot.entities;

public class PomodoroFactory {
    public PomodoroFactory(double workTime, double breakTime, Integer iteration, String name) {
    }

    /**
     * A factory class for Pomodoro.
     *
     * @param workTime: the duration of a work session in a Pomodoro
     * @param breakTime: the duration of a break session in a Pomodoro
     * @param iteration: how many times does a user need work-break flow
     * @param name: the name of a Pomodoro instance, used to identify one
     *
     * Note: I've decided not to use the Factory pattern for Pomodoro.
     */

    public static Pomodoro create(double workTime, double breakTime, Integer iteration, String name){
        return new Pomodoro(workTime, breakTime, iteration, name);
    }
}
