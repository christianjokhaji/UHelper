package ca.unknown.bot.miscellaneous;

import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimerTest {
    /**
     * It's only a class for testing Timer-related methods on the go. You can ignore it.
     */
    public static void main(String[] args) {
        Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        String user = "havefun01(idu003d283783079766786049)";
        ArrayList<Pomodoro> pomodoros = new ArrayList<>();
        pomodoros.add(new Pomodoro(3,2,1,"a"));
        pomodoros.add(new Pomodoro(3,2,1,"b"));
        map.put(user, pomodoros);

        try (FileWriter writer = new FileWriter("timer_repository.json")) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            gson.toJson(map, type, jsonWriter);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
