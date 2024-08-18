package ca.unknown.bot.data_access.schedule_reminder;
import ca.unknown.bot.entities.schedule_reminder.*;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

/**
 * Concrete implementation of a <code>ScheduledReminderDataAccessInterface</code> which persists to .json files
 * and also stores schedules in a cache.
 */
public class ScheduledReminderDAO implements ScheduledReminderDataAccessInterface {
    private Map<String, Schedule> userSchedules = new HashMap<>();
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
        File f = new File(filename);

        // if the program is not running for the first time, then a "src/main/java/ca/unknown/bot/data_access/schedule_reminder/schedule_repository.json"
        // exists in your directory with a set of schedules that were made. this method checks to see if that file exists
        // (so your program has run) and if the DAO mapping is empty, which means that the cache has been restarted
        // even though it should contain prev persisted schedules from the prior occurrences.
        return userSchedules.isEmpty() && f.exists();
    }

    public void saveNewUser(Schedule sched){
        userSchedules.put(sched.getUser(), sched);
        alertCheck.put(sched.getUser(), new ArrayList<String>());
    }

    public void saveToFile(String filename) {
        this.save(filename);
    }

    public void loadRepo(String filename){
        Map<String, LinkedTreeMap> intermediaryRepo = this.loadFile(filename);
        Map<String, Schedule> loadedRepo = this.convertRepo(intermediaryRepo);
        userSchedules = loadedRepo;
    }

    public Schedule getSchedule(String user){
        return userSchedules.get(user);
    }

    public boolean getChecks(String user, String eventName){
        return alertCheck.get(user).contains(eventName);
    }

    public Map<String, Schedule> getRepo(){
        return this.userSchedules;
    }

    public void addExistingCheck(String user, String event){
        alertCheck.get(user).add(event);
    }

    public void addNewCheck(String user){
        alertCheck.put(user, new ArrayList<String>());
    }

    public void removeCheck(String user, String event){
        alertCheck.get(user).remove(event);
    }

    public void removeAllChecks(String user){
        alertCheck.get(user).clear();
    }


    private void save(String filename){

        try(FileWriter writer = new FileWriter(filename)){
            JsonWriter jsonWriter = new JsonWriter(writer);
            Type type = new TypeToken<Map<String, Schedule>>(){}.getType();
            GsonBuilder builder = new GsonBuilder();
            builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
            builder.enableComplexMapKeySerialization();
            Gson gson = builder.create();
            Map<String,Schedule> repo = this.userSchedules;
            gson.toJson(repo, type, jsonWriter);

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private Map<String, LinkedTreeMap> loadFile(String filename){
        try(FileReader filereader = new FileReader(filename)){
            JsonReader jsonReader = new JsonReader(filereader);

            Gson gson = new Gson();
            TypeToken<Map<String, LinkedTreeMap>> typeToken = new TypeToken<>(){};
            Map<String,LinkedTreeMap> repo = gson.fromJson(jsonReader, typeToken.getType());
            return repo;
        } catch(IOException e){
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private Map<String, Schedule> convertRepo(Map<String, LinkedTreeMap> loadedRepo){
        // Converts the intermediary repo into a workable cache for the program

        Map<String, Schedule> userSchedules = new HashMap<>();

        for(String s: loadedRepo.keySet()){
            LinkedTreeMap scheduleIntermediary = loadedRepo.get(s);
            String username = (String) scheduleIntermediary.get("username");

            try {
                String userIdString = (String) scheduleIntermediary.get("userID");
                long userId = Long.parseLong(userIdString);
                ArrayList<LinkedTreeMap> eventsIntermediary = (ArrayList<LinkedTreeMap>) scheduleIntermediary.get("events");

                Schedule finalSchedule = this.convertToSchedule(username, userId, eventsIntermediary);

                userSchedules.put(username, finalSchedule);
            } catch(NumberFormatException n){
                n.printStackTrace();
            }

        }
        return userSchedules;
    }


    private Schedule convertToSchedule(String username, long userId, ArrayList<LinkedTreeMap> events) {
        Schedule sched = new UserSchedule(username, userId);

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a", Locale.ENGLISH);


        for(int i = 0; i < events.size(); i++){
            if(events.get(i).containsKey("location")){
                String location = (String) events.get(i).get("location");
                String courseCode = (String) events.get(i).get("eventName");
                String date = (String) events.get(i).get("eventDate");

                try{
                    Date eventDate = formatter.parse(date);

                    ScheduledEvent exam = new Exam(eventDate, courseCode, location);
                    sched.addEvent(exam);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

            }
            else if(events.get(i).containsKey("courseCode")) {
                String courseCode = (String) events.get(i).get("courseCode");
                String assignmentName = (String) events.get(i).get("eventName");
                String date = (String) events.get(i).get("eventDate");

                try {
                    Date eventDate = formatter.parse(date);

                    ScheduledEvent assignment = new Assignment(eventDate, assignmentName, courseCode);
                    sched.addEvent(assignment);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            else{
                String eventName = (String) events.get(i).get("eventName");
                String date = (String) events.get(i).get("eventDate");

                try {
                    Date eventDate = formatter.parse(date);
                    ScheduledEvent event = new ScheduledEvent(eventDate, eventName);
                    sched.addEvent(event);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return sched;
    }

}