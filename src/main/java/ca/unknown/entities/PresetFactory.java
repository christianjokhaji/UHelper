package ca.unknown.entities;

public interface PresetFactory {
    Pomodoro create(Integer workMinute, Integer breakMinute, Integer iteration, String name);
}
