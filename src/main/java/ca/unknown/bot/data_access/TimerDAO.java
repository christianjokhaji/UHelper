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
import org.apache.commons.lang3.ObjectUtils;

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
    * @param data The desired User-Pomodoro map to be loaded or saved
    * @param filename The name of the timer (Pomodoro) so users can look through the different
    * Pomodoro instances.
    */
    public void savePomodoro(Map userAndTimer, String filename) {

        // 1. Check if user exists in timer_repository
        if (checkUser(userAndTimer, filename)) {
           Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
           GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(User.class, new GSONTypeAdapter(user)); may be used if I
//            change the format of storing a timer setting. Right now, GSONTypeAdapter is dummy.
            Gson gson = builder
                   .enableComplexMapKeySerialization().create();

           // Checks for exceptions since they are common with file operations
           try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               gson.toJson(userAndTimer, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
       }
        // 2. Check if
        else if (checkTimer(userAndTimer, filename)){

        }
    }

    // returns true if user doesn't exist timer_repository
    private boolean checkUser(Map userAndTimer, String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            if (map == null){ return true; }
            return !map.containsKey(userAndTimer.keySet().toArray()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkTimer(Map data, String filename) {
        return true;
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
