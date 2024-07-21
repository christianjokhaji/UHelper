package ca.unknown.bot.entities.timer;

public interface Preset {

    int getWorkTime();

    int getBreakTime();

    int getIteration();

    String toString();

    String getName();
}
