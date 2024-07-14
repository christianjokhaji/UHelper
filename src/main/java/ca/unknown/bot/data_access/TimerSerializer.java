package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Pomodoro;
import net.dv8tion.jda.api.entities.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class TimerSerializer implements JsonSerializer {

    public JsonElement serialize(Pomodoro timer,User user, Type typeOfId, JsonSerializationContext context) {
        return new JsonPrimitive(timer.getName());
   }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
