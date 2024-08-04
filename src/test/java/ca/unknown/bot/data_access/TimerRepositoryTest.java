package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import ca.unknown.bot.entities.timer.Pomodoro;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ca.unknown.bot.data_access.timer.TimerDAO;

import java.io.FileReader;
import java.io.IOException;
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

    private User mockUser1;
    private User mockUser2;
    private String fileName;
    private Pomodoro timer;

    @BeforeEach
    void setUp() {
        timer = new Pomodoro(25.0, 5.0, 3, "test");
        mockSlashEvent = new SlashCommandInteractionEvent(mockJDA, interactionId, mockInteraction);
        mockButtonEvent = mock(ButtonInteractionEvent.class);
        fileName = "src/test/java/ca/unknown/bot/data_access/timer_repository_test.json";
        mockUser1 = (User) User.fromId(12345L);
        mockUser2 = (User) User.fromId(67890L);


        try (FileReader reader = new FileReader(fileName)) {
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            TypeToken<Map<String, ArrayList>> typeToken= new TypeToken
                    <Map<String,ArrayList>>(){};
        } catch (IOException e) {
            e.printStackTrace();}
        }

    @Test //
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
        } catch (IOException e) {
            e.printStackTrace();}
        }

    }
