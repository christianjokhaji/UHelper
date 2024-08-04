    package ca.unknown.bot.entities;

    import ca.unknown.bot.entities.quiz_me.QuizMe;
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
    import org.mockito.Mockito;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    public class QuizMeTest {

        private QuizMe quizMe;
        JDA mockJDA = Mockito.mock(JDA.class);
        SlashCommandInteraction mockInteraction = Mockito.mock(SlashCommandInteraction.class);

        long interactionId = 1000000000L; // placeholder id

        private ButtonInteractionEvent mockButtonEvent;
        private SlashCommandInteractionEvent mockSlashEvent;


        @BeforeEach
        public void setUp() {
            quizMe = new QuizMe();
            //mockSlashEvent = mock(SlashCommandInteractionEvent.class);
            mockSlashEvent = new SlashCommandInteractionEvent(mockJDA, interactionId, mockInteraction);
            mockButtonEvent = mock(ButtonInteractionEvent.class);

            // Mocking reply actions
            InteractionHook mockInteractionHook = mock(InteractionHook.class);
            //when(mockSlashEvent.reply(anyString())).thenReturn(mock(ReplyCallbackAction.class));
            when(mockButtonEvent.reply(anyString())).thenReturn(mock(ReplyCallbackAction.class));
        }

        @Test
        public void testResetNotes() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");
            quizMe.resetNotes(mockSlashEvent);

            assertTrue(quizMe.getNotes().isEmpty());
            assertTrue(quizMe.getHints().isEmpty());
            assertTrue(quizMe.getQuestionsOrder().isEmpty());

            verify(mockSlashEvent).reply("Notes Reset!").queue();
        }


        @Test
        public void testAddQuestionAndAnswer() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");

            assertEquals(1, quizMe.getNotes().size());
            assertEquals(1, quizMe.getHints().size());
            assertEquals(1, quizMe.getQuestionsOrder().size());
            assertEquals("A programming language.", quizMe.getNotes().get("What is Java?"));
            assertEquals("Think about coffee.", quizMe.getHints().get("What is Java?"));
        }

        @Test
        public void testStudyNoQuestions() {
            quizMe.study(mockSlashEvent);

            verify(mockSlashEvent).reply("There are no questions to study.").queue();
        }

        @Test
        public void testStudyWithQuestions() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");
            quizMe.study(mockSlashEvent);

            verify(mockSlashEvent).reply("Question: What is Java?")
                    .addActionRow(any(Button.class), any(Button.class)).queue();
        }

        @Test
        public void testShowAnswer() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");
            quizMe.showAnswer(mockButtonEvent, "What is Java?", 0);

            verify(mockButtonEvent).reply("Answer: A programming language.").addActionRow(any(Button.class)).queue();
        }

        @Test
        public void testShowHint() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");
            quizMe.showHint(mockButtonEvent, "What is Java?", 0);

            verify(mockButtonEvent).reply("Hint: Think about coffee.").queue();
        }

        @Test
        public void testShowNextQuestion() {
            quizMe.addQuestionAndAnswer("What is Java?", "A programming language.", "Think about coffee.");
            quizMe.addQuestionAndAnswer("What is Python?", "Another programming language.", "Think about snakes.");
            MessageChannel mockChannel = mock(MessageChannel.class);
            when(mockButtonEvent.getChannel()).thenReturn((MessageChannelUnion) mockChannel);
            quizMe.showNextQuestion(mockButtonEvent, 0,"Test");

            verify(mockChannel).sendMessage("Question: What is Python?")
                    .setActionRow(any(Button.class), any(Button.class)).queue();
        }
    }
