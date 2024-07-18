package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Pomodoro;
import net.dv8tion.jda.api.entities.User;
import com.google.gson.JsonSerializer;

import java.util.ArrayList;
import java.util.Map;

abstract class AbstractTimerSerializer implements JsonSerializer<Map<User,ArrayList<Pomodoro>>> {
// An abstract class for serializing for timer related stuff; treat it as a dummy for now
}
