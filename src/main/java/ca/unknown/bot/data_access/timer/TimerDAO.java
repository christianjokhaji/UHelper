package ca.unknown.bot.data_access.timer;

import ca.unknown.bot.interface_adapter.timer.TimerController;
import com.google.gson.*;
import ca.unknown.bot.entities.timer.Pomodoro;
import com.google.gson.internal.LinkedTreeMap;
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

public class TimerDAO {
    /**
     * A class in the data access layer. This class communicates with TimerController and
     * TimerPresenter to do the followings :
     * 1) determine the status of timer_repository.json
     * 2) based on its status, it selects a way to write to timer_repository.json
     * 2) fetches data into a readable data structure so that timerPresenter can process
     *
     * Pomodoro instances are stored in the following format as a json file.
     * {user_id: [{"name": "name_of_timer, "map": {"breakTime":5.0,"iteration":3,"workTime":25.0}}]}
     *
     * 1) user_id is a String object that comes from user.toString()
     * 2) user_id is mapped onto ArrayList. It contains the information about a Pomodoro instance,
     * which is retained as an instance of LinkedTreeMap (a custom data structure provided by GSON)
     * 3) A LinkedTreeMap has key-value pairs. In the example above, "map" corresponds to
     * another LinkedTreeMap.
     *
     * @param userAndTimer The desired User-Pomodoro map to be loaded or saved
     * @param filename The name of the timer (Pomodoro) so users can look through the different
     * Pomodoro instances.
     */

    public static void saveTimerOne(Map userAndTimer, String filename) {
        Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.enableComplexMapKeySerialization().create();
        try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               gson.toJson(userAndTimer, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
    }

    public static void saveTimerTwo(Map userAndTimer, String filename) {
            Map repo = loadPomodoro(filename); // Copies the repository into a hashmap
            String key = userAndTimer.keySet().toArray()[0].toString(); // turn user instance into a string
            repo.put(key, userAndTimer.get(key)); // adds the new user-ArrayList<Pomodoro>
            Gson gson = new GsonBuilder().create();
            try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
               gson.toJson(repo, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
    }

    public static void saveTimerThree(Map userAndTimer, String filename) {
            Map repo = loadPomodoro(filename);
            String key = userAndTimer.keySet().toArray()[0].toString();
            ArrayList value = (ArrayList) userAndTimer.get(key);
            ArrayList newPomodoros = (ArrayList) repo.get(key);
            Pomodoro pomodoro = (Pomodoro) value.get(0);

            // 1: Encase a Pomodoro instance from userAndTimer.get(key) in a LinkedTreeMap
            LinkedTreeMap timer = TimerController.convertToLTM(pomodoro);

            // 2: Put the new LinkedTreeMap in newPomodoros, which is an ArrayList
            newPomodoros.add(timer);

            // 3: repo.put(key, newPomodoros);
            repo.put(key, newPomodoros);
            Gson gson = new GsonBuilder().create();
            try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
               gson.toJson(repo, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
    }


    public static void deletePomodoro(String name, String user, String filename) {
        Map repo = loadPomodoro(filename);
        ArrayList pomodoros = (ArrayList) repo.get(user);
        LinkedTreeMap deleted = null;
        for (Object pomodoro : pomodoros){
            LinkedTreeMap instance = (LinkedTreeMap) pomodoro;
            if (instance.containsValue(name)) {
                deleted = instance;
            }
        }
        pomodoros.remove(deleted);
        repo.put(user, pomodoros);
        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(filename)) {
               JsonWriter jsonWriter = new JsonWriter(writer);
               Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
               gson.toJson(repo, type, jsonWriter);
           } catch (IOException e) { // This will be raised if filename DNE in local
               e.printStackTrace();
           }
    }

    // helper function: returns true if the designated json file is totally empty
    public static boolean checkEmpty(String filename) {
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

    // helper function: returns true if the input user is not found in the json file
    public static boolean checkUser(Map userAndTimer, String filename) {
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

    public static boolean checkUserDelete(String user, String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            return map.containsKey(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // returns true if user has 5 or more timer instances
    public static boolean checkMoreThanFive(String user) {
        try (FileReader reader = new FileReader("timer_repository.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map map = gson.fromJson(jsonReader, typeToken.getType());
            if (map == null) {return false;}
            else if (!map.containsKey(user)) {return false;}
            ArrayList timers = (ArrayList) map.get(user);
            return timers.size() >= 5;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // returns true if the input name is already used for any timer that user has
    public static boolean checkDuplicate(String name, String user) {
        try (FileReader reader = new FileReader("timer_repository.json")) {
            Map repo = loadPomodoro("timer_repository.json");
            if (repo == null) {return false;}
            else if (!repo.containsKey(user)) {return false;}
            ArrayList Pomodoros = (ArrayList) repo.get(user);
            for (int i = 0; i < Pomodoros.size(); i++) {
                LinkedTreeMap timer = (LinkedTreeMap) Pomodoros.get(i);
                String timerName = (String) timer.get("name");
                if (timerName.equals(name)) {return true;}
                }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<LinkedTreeMap> loadTimers(String user, String filename) {
   /**
    * loadTimers returns an ArrayList that consists of Pomodoro instances.
    *
    * @param user an identifier that serves to find out what list to import
    * @param filename the location of timer_repository
    * @return ArrayList of Pomodoro instances
    */
        Map repo = loadPomodoro(filename);
        if (repo == null) { // if timer_repository is empty
            ArrayList emptyList = new ArrayList();
            return emptyList;
        }
        else if (repo.containsKey(user)) { // if you do have a timer
            ArrayList list = (ArrayList) repo.get(user);
            return list;
        }
        else { // if you haven't created a timer yet
            ArrayList emptyList = new ArrayList();
            return emptyList;
        }
    }

   public static Map loadPomodoro(String filename) {
    /**
    * loadPomodoro is a reader method that fetches the timer-related data from timer_repository.json
    * as a map. The map contains a string-fied user identifier and an arraylist, which consists of
    * Pomodoro objects, as key and value respectively.
    *
    * @param filename the location of timer_repository (or any other json file)
    * @return A map representation of timer_repository.json
    */
       try (FileReader reader = new FileReader(filename)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String,ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map repo = gson.fromJson(jsonReader, typeToken.getType());
            return repo;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return new HashMap();
   }
}
