package ca.unknown.bot.data_access.timer;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import ca.unknown.bot.entities.timer.Pomodoro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ca.unknown.bot.data_access.timer.TimerDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TimerRepositoryTest {
    /**
     * Please completely empty timer_repository_test.json before conducting this test.
     */

    JDA mockJDA = Mockito.mock(JDA.class);
    SlashCommandInteraction mockInteraction = Mockito.mock(SlashCommandInteraction.class);

    long interactionId = 1000000000L; // placeholder id

    private ButtonInteractionEvent mockButtonEvent;
    private SlashCommandInteractionEvent mockSlashEvent;

    private UserSnowflake mockUser1;
    private UserSnowflake mockUser2;
    private String fileName;
    private Pomodoro timer;

    @BeforeEach
    void setUp() {
        timer = new Pomodoro(25.0, 5.0, 3, "test");
        mockSlashEvent = new SlashCommandInteractionEvent(mockJDA, interactionId, mockInteraction);
        mockButtonEvent = mock(ButtonInteractionEvent.class);
        fileName = "src/test/java/ca/unknown/bot/data_access/timer/timer_repository_test.json";
        mockUser1 = User.fromId(123L);
        mockUser2 = User.fromId(456L);

        try (FileReader reader = new FileReader(fileName)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String, ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // Tests when a single user adds their timer for the first time
    void newTimerWhenEmpty() {
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(timer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(mockUser1.toString(), value);

        TimerDAO.saveTimerOne(userAndTimer, fileName);

        try (FileReader reader = new FileReader(fileName)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String, ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map result = gson.fromJson(jsonReader, typeToken.getType());
            assert(result.containsKey(mockUser1.toString()));
            ArrayList content = (ArrayList) result.get(mockUser1.toString());
            LinkedTreeMap timer = (LinkedTreeMap) content.get(0);
            assertEquals("test", timer.get("name"));
            LinkedTreeMap spec = (LinkedTreeMap) timer.get("map");
            assertEquals(5.0, spec.get("breakTime"));
            assertEquals(25.0, spec.get("workTime"));
            assertEquals(3.0, spec.get("iteration"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // Tests whether deleting a timer works
    void checkDeletePomodoro() {
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(timer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(mockUser1.toString(), value);

        TimerDAO.saveTimerOne(userAndTimer, fileName);

        TimerDAO.deletePomodoro("test", mockUser1.toString(), fileName);

        try (FileReader reader = new FileReader(fileName)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String, ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
            Map result = gson.fromJson(jsonReader, typeToken.getType());
            assert(result.containsKey(mockUser1.toString()));
            ArrayList content = (ArrayList) result.get(mockUser1.toString());
            assert(content.isEmpty());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // Check TimerDAO.checkEmpty()
    void checkCheckEmpty() {
        assertEquals(false, TimerDAO.checkEmpty(fileName));

        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(timer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(mockUser1.toString(), value);

        TimerDAO.saveTimerOne(userAndTimer, fileName);
        assertEquals(false, TimerDAO.checkEmpty(fileName));
    }

    @Test // Check if it returns true for an existing user and false for a non-existing user
    void checkCheckUser() {
        ArrayList<Pomodoro> value = new ArrayList<>();
        value.add(timer);
        Map userAndTimer = new HashMap();
        userAndTimer.put(mockUser1.toString(), value);

        TimerDAO.saveTimerOne(userAndTimer, fileName);

        assertEquals(true, TimerDAO.checkUser(mockUser1.toString(), fileName));
        assertEquals(false, TimerDAO.checkUser(mockUser2.toString(), fileName));
    }

    @AfterEach
    void cleanUp() throws IOException {
        Map clean = new HashMap();

        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            Type type = new TypeToken<Map<String,ArrayList<Pomodoro>>>(){}.getType();
            gson.toJson(clean, type, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
