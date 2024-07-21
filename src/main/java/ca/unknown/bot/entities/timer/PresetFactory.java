package ca.unknown.bot.entities.timer;

public interface PresetFactory {
    Pomodoro create(Float workMinute, Float breakMinute, Integer iteration, String name);
}
