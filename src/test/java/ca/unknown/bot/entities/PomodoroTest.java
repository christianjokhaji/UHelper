    package ca.unknown.bot.entities;

    import net.dv8tion.jda.api.JDA;
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

    import java.util.HashMap;
    import ca.unknown.bot.entities.timer.Pomodoro;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertTrue;
    import static org.mockito.Mockito.*;

    public class PomodoroTest {

    /**
    * A unit test class for the Pomodoro entity
    *
    * I will add more tests as I finish implementing /timer_start and /timer_cancel. As of now,
     * I realized that it's not feasible to test this class other than its constructor and getters.
    */

        private Pomodoro test;
        JDA mockJDA = Mockito.mock(JDA.class);
        SlashCommandInteraction mockInteraction = Mockito.mock(SlashCommandInteraction.class);

        long interactionId = 1000000000L; // placeholder id

        private ButtonInteractionEvent mockButtonEvent;
        private SlashCommandInteractionEvent mockSlashEvent;

        @BeforeEach
        public void setUp() {

            test = new Pomodoro(25.0, 5.0, 3, "test");
            mockSlashEvent = new SlashCommandInteractionEvent(mockJDA, interactionId, mockInteraction);
            mockButtonEvent = mock(ButtonInteractionEvent.class);

            InteractionHook mockInteractionHook = mock(InteractionHook.class);
            when(mockButtonEvent.reply(anyString())).thenReturn(mock(ReplyCallbackAction.class));

        }

        @Test // Check if the constructor and getters of Pomodoro works correctly
        void constructorCheck() {
            assertEquals(25.0, test.getWorkTime());
            assertEquals(5.0, test.getBreakTime());
            assertEquals(3, test.getIteration());
            assertEquals("test", test.getName());
            HashMap map = new HashMap<>();
            map.put("workTime", 25.0);
            map.put("breakTime", 5.0);
            map.put("iteration", 3);
            assertEquals(map, test.getMap());
        }

        @Test // Check if a Pomodoro instance has a correct string representation
        void toStringCheck() {
            String str = test.toString();
            assertEquals(str, "test: 25.0 minutes of work, 5.0 minutes of break for 3" +
                    " times.");
        }
    }
