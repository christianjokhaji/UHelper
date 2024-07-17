package ca.unknown.bot.entities;

public interface PresetFactory {
    // TBH I just followed the CleanArchitecture example.

    /**
     * An interface for making a Pomodoro instance.
     */
    public static Pomodoro create(double workMinute, double breakMinute, Integer iteration, String name) {
        return new Pomodoro(workMinute, breakMinute, iteration, name);
    }
}
