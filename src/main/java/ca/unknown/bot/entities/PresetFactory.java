package ca.unknown.bot.entities;

public interface PresetFactory {
    Pomodoro create(Float workMinute, Float breakMinute, Integer iteration, String name);
}
