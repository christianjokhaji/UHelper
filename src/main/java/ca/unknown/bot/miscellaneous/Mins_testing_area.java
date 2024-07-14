package ca.unknown.bot.miscellaneous;

import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

public class Mins_testing_area {
    public static void main(String[] args) {

        Pomodoro timer = new Pomodoro(3,2,1,"a");
        Pomodoro timer2 = new Pomodoro(5,4,3,"b");
        String user = new String("user");
        HashMap hashMap = new HashMap();
        ArrayList<Pomodoro> pomodoros = new ArrayList<>();
        pomodoros.add(timer);
        pomodoros.add(timer2);
        hashMap.put(user, pomodoros);

        TypeToken PomodoroType = new TypeToken<HashMap<String,ArrayList<Pomodoro>>>(){};
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(hashMap, PomodoroType.getType());


        // Checks for exceptions since they are common with file operations
        try (FileWriter writer = new FileWriter("timer_repository.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        try (FileReader reader = new FileReader("test.json")) {
//            // gson.fromJson converts from the json file we have back to a Java object
//            Pomodoro obj = gson.fromJson(reader, Pomodoro.class);
//            System.out.println(obj);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




    }
}
