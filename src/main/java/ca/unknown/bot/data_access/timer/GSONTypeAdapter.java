package ca.unknown.bot.data_access.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GSONTypeAdapter extends TypeAdapter<Map<String, ArrayList<Pomodoro>>> {

    /**
     * GSONTypeAdapter--it's a placeholder name--is a class for the GSON library. Extending
     * from TypeAdapter, It allows the users of GSON to customize how Java objects are serialized
     * into a json file. This class, when creating a GSON instance using GSONBUILDER, can be
     * attached as an option so that your desired Java objects are saved differently from
     * the default settings. Currently, the class's methods are empty after choosing not to use them
     * for now.
     *
     * If you wish to learn more about how to customize serialization in GSON, please visit
     * https://www.javadoc.io/static/com.google.code.gson/gson/2.11.0/com.google.gson/com/google/gson/JsonSerializer.html
     *
     * There exists a class called TimerSerializer, which I designed for the same purpose above.
     * This is also a dummy class, so you can ignore it. But, I (Min) may choose to adopt it in
     * the future.
     */


    @Override // Customizes they way an object (for example, userAndTimer in TimerDAO) would be
    // represented as a json file
    public void write(JsonWriter out, Map<String, ArrayList<Pomodoro>> value) throws IOException {
    }

    @Override // Customizes the way GSON converts a json file into a java object
    public Map read(JsonReader in) throws IOException {
        return null;
    }
}
