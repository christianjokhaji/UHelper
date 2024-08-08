    package ca.unknown.bot.entities.quiz_me;


    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;


    import static org.junit.jupiter.api.Assertions.*;

    public class QuizMeTest {

        private QuizMe quizMe;

        @BeforeEach
        public void setUp() {
            quizMe = new QuizMe();
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


    }
