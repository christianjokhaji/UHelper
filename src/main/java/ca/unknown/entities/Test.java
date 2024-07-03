package ca.unknown.entities;

import ca.unknown.entities.Pomodoro;

public class Test {
    public static void main(String[] args) {
        String command = "preset 3 1 1 lol";
        try {
                String[] parts = command.split(" ");
                Integer workMinute = Integer.parseInt(parts[1]);
                Integer breakMinute = Integer.parseInt(parts[2]);
                Integer iterations = Integer.parseInt(parts[3]);
                String name = parts[4];
                Pomodoro newTimer = new Pomodoro(workMinute, breakMinute, iterations, name);
                newTimer.commenceTimer();
            }
            catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
    }


}
