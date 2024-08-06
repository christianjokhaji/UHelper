package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.Schedule;
import ca.unknown.bot.entities.schedule_reminder.ScheduleInstanceCreator;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.util.*;
import java.io.*;

/**
 * Concrete implementation of a <code>ScheduledReminderDataAccessInterface</code> which persists to .json files
 * and also stores schedules in a cache.
 */
public class ScheduledReminderDAO implements ScheduledReminderDataAccessInterface {
    /**
     * The in-memory cache of the program.
     */
    private Map<String, Schedule> userSchedules = new HashMap<>();

    /**
     * Stores the events that users should be alerted of.
     */
    private Map<String, ArrayList<String>> alertCheck = new HashMap<>();

    /**
     * Class constructor.
     */
    public ScheduledReminderDAO(){

    }

    public boolean existsByUser(String user) {
        return userSchedules.containsKey(user);
    }

    public boolean emptyCache(String filename){
        File f = new File(filename + ".json");

        // if the program is not running for the first time, then a "schedule_repository.json" exists
        // in your directory with a set of schedules that were made. this method checks to see if that file exists
        // (so your program has run) and if the DAO mapping is empty, which means that the cache has been restarted
        // even though it should contain prev persisted schedules from the prior occurrences.
        return userSchedules.isEmpty() && f.exists();
    }

    public void saveNewUser(Schedule sched){
        userSchedules.put(sched.getUser(), sched);
        alertCheck.put(sched.getUser(), new ArrayList<String>());
    }

    @Override
    public void saveToFile(String filename) {
        this.save(filename + ".json");
    }

    public void loadRepo(String filename){
        userSchedules = this.load(filename + ".json");
    }

    public Schedule getSchedule(String user){
        return userSchedules.get(user);
    }

    public boolean getChecks(String user, String eventName){
        return alertCheck.get(user).contains(eventName);
    }

    public void addCheck(String user, String event){
        alertCheck.get(user).add(event);
    }

    public void removeCheck(String user, String event){
        alertCheck.get(user).remove(event);
    }

    public void removeAllChecks(String user){
        alertCheck.get(user).clear();
    }

    /**
     * Persists the current cache to a .json file.
     * @param filename the .json file to persist to
     */
    private void save(String filename){

        try(FileWriter writer = new FileWriter(filename)){
            JsonWriter jsonWriter = new JsonWriter(writer);
            Type type = new TypeToken<Map<String, Schedule>>(){}.getType();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.enableComplexMapKeySerialization().create();
            Map<String,Schedule> repo = this.userSchedules;
            gson.toJson(repo, type, jsonWriter);

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Loads a repository of user schedules from a .json file and saves it to the program's current cache.
     * @param filename the .json file to read from
     * @return a new <code>Map<String, Schedule></code> cache of the loaded repo
     */
    private Map<String, Schedule> load(String filename){
        try(FileReader filereader = new FileReader(filename)){
            JsonReader jsonReader = new JsonReader(filereader);

            // reading in doesn't work yet because of incompatible interface types when converting from json to gson
            // so the cache cannot persist if the program restarts itself but the .json file still contains the correct
            // data which was written to it when saving the cache to a repo
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Schedule.class, new ScheduleInstanceCreator(""));
            Gson gson = new Gson();
            TypeToken<Map<String,Schedule>> typeToken = new TypeToken<>(){};
            Map<String,Schedule> repo = gson.fromJson(jsonReader, typeToken.getType());
            return repo;
        } catch(IOException e){
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}