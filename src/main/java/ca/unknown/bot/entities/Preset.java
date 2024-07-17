package ca.unknown.bot.entities;

public interface Preset {

    /**
     * A common interface for timer.
     * For the purpose of satisfying the initial specifications of the project,
     * the only type of timer implemented is Pomodoro. Yet, this interface should be useful when
     * it comes to designing other types of timer (i.e. general, sleep alarm, etc.)
     */

    int getWorkTime();

    int getBreakTime();

    int getIteration();

    String toString();

    String getName();
}
