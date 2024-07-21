package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.Schedule;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.util.*;
import java.io.*;

public class ScheduledReminderDAO implements ScheduledReminderDataAccessInterface {
    private Map<String, Schedule> userSchedules = new HashMap<>();

    public ScheduledReminderDAO(){

    }

    @Override
    public boolean existsByUser(String user) {
        return userSchedules.containsKey(user);
    }

    public void saveNewUser(Schedule sched){
        userSchedules.put(sched.getUser(), sched);
    }

    @Override
    public void saveToFile(String filename) {
        this.save(filename + ".json");
    }


    public Schedule getSchedule(String user){
        return userSchedules.get(user);
    }

    private void save(String filename){

        try(FileWriter writer = new FileWriter(filename)){
            JsonWriter jsonWriter = new JsonWriter(writer);
            Type type = new TypeToken<Map<String,Schedule>>(){}.getType();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.enableComplexMapKeySerialization().create();
            Map repo = this.userSchedules;
            gson.toJson(repo, type, jsonWriter);

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private void load(){
        // put in map from file
    }
}