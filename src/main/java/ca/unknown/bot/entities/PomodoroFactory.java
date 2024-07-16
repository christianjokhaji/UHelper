package ca.unknown.bot.entities;

public class PomodoroFactory implements PresetFactory {
    /**
     * @param workTime: the duration of a work session in a Pomodoro
     * @param breakTime: the duration of a break session in a Pomodoro
     * @param iteration: how many times does a user need work-break flow
     * @param name: the name of a Pomodoro instance, used to identify one
     * @return
     */
    @Override
    public Pomodoro create(Float workTime, Float breakTime, Integer iteration, String name){
        return new Pomodoro(workTime, breakTime, iteration, name);
    }
}
