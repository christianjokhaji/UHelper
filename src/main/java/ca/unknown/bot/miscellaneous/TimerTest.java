package ca.unknown.bot.miscellaneous;

import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class TimerTest {
    /**
     * It's only a class for testing Timer-related methods on the go. You can ignore it.
     *
     * Formal testings won't be performed in this class. The proper test file for timer can be found
     * in test.java.ca.unknown.bot.entities.PomodoroTest.
     */
    public static void main(String[] args) {
        TimerDAO timerDAO = new TimerDAO();
        Map repo = timerDAO.loadPomodoro("timer_repository.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    }
}
