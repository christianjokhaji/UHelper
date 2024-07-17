package ca.unknown.bot.data_access;

import com.google.gson.*;
import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dv8tion.jda.api.entities.User;
import ca.unknown.bot.use_cases.Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class TimerDAO {
    /**
    * How are Pomodoro objects are stored? TimerDAO will categorize every instance by
    * the Discord User that created it by using /timer_create.
    * <p></p>
    * {User1: {timer1_name: {"breakTime":2.0,"iteration":1,"workTime":3.0}, timer2_name:
    *     {"breakTime":9.0,"iteration":100,"workTime":3.14}
    * }, User2: ...}
    */

   /**
    * Notes:
    * 1) GsonBuilder is a builder class that is used when one wants to create a Gson object with
    * custom configurations. For details, visit
    *
    * @param data The desired Pomodoro object to be loaded or saved
    * @param filename The name of the timer (Pomodoro) so users can look through the different
    * Pomodoro instances.
    */
    public void savePomodoro(Map data, String filename) {
          // 1.Checks if the user DNE in json
           Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
           GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(User.class, new GSONTypeAdapter(user));
// This custom typeadapter may be used if I change the way the bot structures user and timer
           Gson gson = builder
                   .enableComplexMapKeySerialization().create();

           // Checks for exceptions since they are common with file operations
           try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               gson.toJson(data, type, jsonWriter);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    private boolean checkUser(Gson gson, Map data, String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
            Map checking = gson.fromJson(jsonReader, type);
            return !checking.containsKey(data.keySet().toArray()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

   public Pomodoro loadPomodoro(String filename) {
       /**
    *
    * @param filename Name of quiz object to be loaded
    * @return Pomodoro using file reader
    */
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
