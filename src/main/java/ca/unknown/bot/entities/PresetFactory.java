package ca.unknown.bot.entities;

public interface PresetFactory {
    Pomodoro create(Integer workMinute, Integer breakMinute, Integer iteration, String name);
}
