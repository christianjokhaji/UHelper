    package ca.unknown.bot.entities;

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
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.AfterAll;
    import org.mockito.Mockito;

    import java.util.ArrayList;
    import java.util.HashMap;
    import ca.unknown.bot.entities.timer.Pomodoro;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertTrue;
    import static org.mockito.Mockito.*;

    public class PomodoroTest {

    /**
    * A unit test class for the Pomodoro entity
    */

        private Pomodoro timer;
        JDA mockJDA = Mockito.mock(JDA.class);
        SlashCommandInteraction mockInteraction = Mockito.mock(SlashCommandInteraction.class);

        long interactionId = 1000000000L; // placeholder id

        private ButtonInteractionEvent mockButtonEvent;
        private SlashCommandInteractionEvent mockSlashEvent;

        private User mockUser;

        @BeforeEach
        public void setUp() {
            timer = new Pomodoro(25.0, 5.0, 3, "test");
            mockSlashEvent = new SlashCommandInteractionEvent(mockJDA, interactionId, mockInteraction);
            mockButtonEvent = mock(ButtonInteractionEvent.class);

            InteractionHook mockInteractionHook = mock(InteractionHook.class);
            when(mockButtonEvent.reply(anyString())).thenReturn(mock(ReplyCallbackAction.class));
        }

        @Test // Check if the constructor and getters of Pomodoro works correctly
        void constructorCheck() {
            assertEquals(25.0, timer.getWorkTime());
            assertEquals(5.0, timer.getBreakTime());
            assertEquals(3, timer.getIteration());
            assertEquals("test", timer.getName());
            HashMap map = new HashMap<>();
            map.put("workTime", 25.0);
            map.put("breakTime", 5.0);
            map.put("iteration", 3);
            assertEquals(map, timer.getMap());
        }

        @Test // Check if a Pomodoro instance has a correct string representation
        void toStringCheck() {
            String str = timer.toString();
            assertEquals(str, "test - 25.0 minutes of work, 5.0 minutes of break for 3" +
                    " time(s)");
        }

        @Test
        void userCheck() { // Check if an instance's user repository works as intended
            assert(timer.getUsers().isEmpty());
            timer.addUser(mockUser);
            assert(timer.getUsers().contains(mockUser) && timer.getUsers().size() == 1);
            timer.removeUser(mockUser);
            assert(timer.getUsers().isEmpty());
        }
    }
