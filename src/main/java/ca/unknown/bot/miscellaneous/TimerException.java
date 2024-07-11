package ca.unknown.bot.miscellaneous;

import java.util.ArrayList;

public class TimerException extends RuntimeException {
    public TimerException(String message) {
        if (true) throw new TimerException("You can't have more than three timer settings.");
    }
}
