package ca.unknown.bot.entities.timer;

import java.util.HashMap;

public interface TimerInterface {

    /**
     * A common interface for timer.
     * For the purpose of satisfying the initial specifications of the project,
     * the only type of timer implemented is Pomodoro. Yet, this interface should be useful when
     * it comes to designing other types of timer (i.e. general, sleep alarm, etc.)
     */

    double getWorkTime();

    double getBreakTime();

    int getIteration();

    HashMap getMap();

    String toString();

    String getName();
}
