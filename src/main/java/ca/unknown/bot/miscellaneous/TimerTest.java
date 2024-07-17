package ca.unknown.bot.miscellaneous;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.use_cases.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
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
        String user = "User:havefun01(idu003d283783079766786049)";
        ArrayList<Pomodoro> pomodoros = new ArrayList<>();
        pomodoros.add(new Pomodoro(3,2,1,"a"));
        pomodoros.add(new Pomodoro(3,2,1,"b"));
        map.put(user, pomodoros);

//        try (FileWriter writer = new FileWriter("timer_repository.json")) {
//            JsonWriter jsonWriter = new JsonWriter(writer);
//            gson.toJson(map, type, jsonWriter);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        try (FileReader reader = new FileReader("timer_repository.json")){
            JsonReader jsonReader = new JsonReader(reader);
            Map checking = gson.fromJson(jsonReader, Map.class);
            System.out.println(checking);
            System.out.println(checking.keySet().toArray()[0]);
            System.out.println(checking.keySet().toArray()[0].toString() == user);
        } catch (IOException e) {
            e.printStackTrace();}
    }
}
