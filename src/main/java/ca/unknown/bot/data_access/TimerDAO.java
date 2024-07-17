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
    * @param userAndTimer The desired User-Pomodoro map to be loaded or saved
    * @param filename The name of the timer (Pomodoro) so users can look through the different
    * Pomodoro instances.
    */
    public void savePomodoro(Map userAndTimer, String filename) {

        // 1. Check timer_repository is empty
        if (checkEmpty(userAndTimer, filename)) {
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
        // 2. Check if timer_repository doesn't contain that specific user
        else if (!checkUser(userAndTimer, filename)){
            Map map = loadPomodoro(filename);
            String key = userAndTimer.keySet().toArray()[0].toString();
            map.put(key, userAndTimer.get(key));
            Gson gson = new GsonBuilder().create();
            try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
               gson.toJson(map, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
        }
        // 3. Check if timer_repository contains the user but no specified timer
        else if (checkUser(userAndTimer, filename)){

        }
    }

    // returns true if the designated json file is totally empty
    private boolean checkEmpty(Map userAndTimer, String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            return map == null; //
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // returns true if the input user is not found in the json file
    private boolean checkUser(Map userAndTimer, String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            return map.containsKey(userAndTimer.keySet().toArray()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkTimer(Map userAndTimer, String filename) {
        return true;
    }

   public Map loadPomodoro(String filename) {
       /**
    *
    * @param filename Name of quiz object to be loaded
    * @return Pomodoro using file reader
    */
       try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return new HashMap();
   }
}
