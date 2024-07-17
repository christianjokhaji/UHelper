package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class TimerSerializer extends AbstractTimerSerializer {
    @Override
    public JsonObject serialize(Map<User, ArrayList<Pomodoro>> src, Type typeOfSrc,
                                JsonSerializationContext context) {
       return new JsonObject();
    }
}
