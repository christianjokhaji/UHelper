package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.timer.Pomodoro;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class TimerSerializer extends AbstractTimerSerializer {
    /**
     * Please read the Javadoc of GSONTypeAdapter if you'd like to know what I (Min) wanted to do
     * with this. This class is not being used now.
     *
     * If you wish to learn more about how to customize serialization in GSON, please visit
     * https://www.javadoc.io/static/com.google.code.gson/gson/2.11.0/com.google.gson/com/google/gson/JsonSerializer.html
     *
     */


    @Override
    public JsonObject serialize(Map<User, ArrayList<Pomodoro>> src, Type typeOfSrc,
                                JsonSerializationContext context) {
       return new JsonObject();
    }
}
