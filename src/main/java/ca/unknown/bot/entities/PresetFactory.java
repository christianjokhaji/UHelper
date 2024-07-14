package ca.unknown.bot.entities;

public interface PresetFactory {
    // TBH I just followed the CleanArchitecture example
    Pomodoro create(Float workMinute, Float breakMinute, Integer iteration, String name);
}
