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

    @Override
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
     * Loads a repository of user schedules from a .json file into an intermediary form.
     * @param filename the .json file to read from
     * @return a new <code>Map<String, LinkedTreeMap></code> of the loaded intermediary repo
     */
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

    /**
     * Converts the intermediary repo into a workable cache for the program.
     * @param loadedRepo the intermediary form of the schedule repo
     * @return a new cache repo
     */
    private Map<String, Schedule> convertRepo(Map<String, LinkedTreeMap> loadedRepo){
        Map<String, Schedule> userSchedules = new HashMap<>();

        for(String s: loadedRepo.keySet()){
            LinkedTreeMap scheduleIntermediary = loadedRepo.get(s);
            String user = (String) scheduleIntermediary.get("user");
            ArrayList<LinkedTreeMap> eventsIntermediary = (ArrayList<LinkedTreeMap>) scheduleIntermediary.get("events");

            Schedule finalSchedule = this.convertToSchedule(user, eventsIntermediary);

            userSchedules.put(user, finalSchedule);
        }
        return userSchedules;
    }

    /**
     * Converts LinkedTreeMap representations of <code>ScheduledEvent</code>s into a <code>Schedule</code> entity.
     * @param events the intermediary form of the user's scheduled events
     * @return a new Schedule for the user
     */
    private Schedule convertToSchedule(String user, ArrayList<LinkedTreeMap> events) {
        Schedule sched = new UserSchedule(user);

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