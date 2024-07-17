package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Pomodoro;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GSONTypeAdapter extends TypeAdapter<Map<String, ArrayList<Pomodoro>>> {


    @Override
    public void write(JsonWriter out, Map<String, ArrayList<Pomodoro>> value) throws IOException {

    }

    @Override
    public Map read(JsonReader in) throws IOException {
        return null;
    }
}
