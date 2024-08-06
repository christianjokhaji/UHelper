package ca.unknown.bot.data_access.quiz_me;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONQuizMeRepositoryTest {

    private JSONQuizMeRepository repository;
    private QuizMe quizMe;

    @BeforeEach
    public void setUp() {
        repository = new JSONQuizMeRepository();
        quizMe = new QuizMe();
        quizMe.getNotes().put("note1", "This is a note");
        quizMe.getHints().put("hint1", "This is a hint");
        quizMe.getQuestionsOrder().add("question1");
    }


    // Don't want testing file as part of quizzes after testing is done
    @AfterEach
    public void tearDown() {
        File file = new File("quizzes/test_quiz.json");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveQuizMe() {
        repository.saveQuizMe(quizMe, "test_quiz.json");
        File file = new File("quizzes/test_quiz.json");
        assertTrue(file.exists());
    }

    @Test
    public void testLoadQuizMe() {
        repository.saveQuizMe(quizMe, "test_quiz.json");
        QuizMe loadedQuizMe = repository.loadQuizMe("test_quiz.json");

        assertEquals(quizMe.getNotes(), loadedQuizMe.getNotes());
        assertEquals(quizMe.getHints(), loadedQuizMe.getHints());
        assertEquals(quizMe.getQuestionsOrder(), loadedQuizMe.getQuestionsOrder());
    }
}
