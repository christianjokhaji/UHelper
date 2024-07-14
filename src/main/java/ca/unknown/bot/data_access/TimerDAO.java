package ca.unknown.bot.data_access;

import com.google.gson.*;
import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.reflect.TypeToken;
import net.dv8tion.jda.api.entities.User;
import ca.unknown.bot.data_access.TimerDataSet;
import ca.unknown.bot.use_cases.Parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;


public class TimerDAO {
    /**
     * How are Pomodoro objects are stored? TimerDAO will categorize every instance by
     * the Discord User that created it by using /timer_create.
     *
     * {User1: {timer1_name: {"breakTime":2.0,"iteration":1,"workTime":3.0}, timer2_name:
     *     {"breakTime":9.0,"iteration":100,"workTime":3.14}
     * }, User2: ...}
     */

    /**
     * Notes:
     * 1) GsonBuilder is a builder class that is used when one wants to create a Gson object with
     * custom configurations. For details, visit
     * https://www.javadoc.io/static/com.google.code.gson/gson/2.11.0/com.google.gson/com/google/gson/GsonBuilder.html
     *
     *
     * @param timer The desired Pomodoro object to be loaded or saved
     * @param filename The name of the timer (Pomodoro) so users can look through the different
     * Pomodoro instances.
     */
    public void savePomodoro(Pomodoro timer, User user, String filename) {

        if (checkUser(user, filename)){

        } else if (checkTimer(user, timer, filename)) {

        } else if (checkDuplicate(user, timer, filename)) {

        }


        TypeToken<Pomodoro> PomodoroType = new TypeToken<>(){};
        Gson gson = new GsonBuilder().registerTypeAdapter(Pomodoro.class, new TimerSerializer())
                .create();
        String json = gson.toJson(timer, PomodoroType.getType());

        // Checks for exceptions since they are common with file operations
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUser(User user, String filename) {
        JsonObject jsonObject = Parser.parse(filename);
        Map map = jsonObject.asMap();

        return true;
    }

    private boolean checkTimer(User user, Pomodoro timer, String filename) {
        return true;
    }

    private boolean checkDuplicate(User user, Pomodoro timer, String filename){
        return true;
    }

    /**
     *
     * @param filename Name of quiz object to be loaded
     * @return Pomodoro using file reader
     */
    public Pomodoro loadPomodoro(String filename) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filename)) {
            // gson.fromJson converts from the json file we have back to a Java object
            return gson.fromJson(reader, Pomodoro.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(filename, Pomodoro.class);
    }
}
