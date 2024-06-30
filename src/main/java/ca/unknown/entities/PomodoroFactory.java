package ca.unknown.entities;

public class PomodoroFactory implements PresetFactory {
    /**
     * @param workMinute: the duration of a work session in a Pomodoro
     * @param breakMinute: the duration of a break session in a Pomodoro
     * @param iteration: how many times does a user need work-break flow
     * @param name: the name of a Pomodoro instance, used to identify one
     * @return
     */
    @Override
    public Pomodoro create(Integer workMinute, Integer breakMinute, Integer iteration, String name){
        return new Pomodoro(workMinute, breakMinute, iteration, name);
    }
}
