package ca.unknown.bot.entities.quiz_me;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizTakerTest {

    private QuizTaker quizTaker;

    @BeforeEach
    public void setUp() {
        quizTaker = new QuizTaker("user");
    }

    @Test
    public void testGetUserId() {
        assertEquals("user", quizTaker.getUserId());
    }

    @Test
    public void testInitialScore() {
        assertEquals(0, quizTaker.getCurrentScore());
    }

    @Test
    public void testIncrementScore() {
        quizTaker.incrementScore();
        assertEquals(1, quizTaker.getCurrentScore());
    }

    @Test
    public void testResetScore() {
        quizTaker.incrementScore();
        quizTaker.resetScore();
        assertEquals(0, quizTaker.getCurrentScore());
    }

    @Test
    public void testRecordAttempt() {
        quizTaker.recordAttempt("Question1");
        quizTaker.recordAttempt("Question1");
        quizTaker.recordAttempt("Question2");

        assertEquals(2, quizTaker.getQuestionAttempts().get("Question1").intValue());
        assertEquals(1, quizTaker.getQuestionAttempts().get("Question2").intValue());
    }

    @Test
    public void testRecordCorrectAnswer() {
        quizTaker.recordCorrectAnswer("Question1");
        quizTaker.recordCorrectAnswer("Question1");

        assertEquals(2, quizTaker.getCorrectAnswers().get("Question1").intValue());
    }

    @Test
    public void testAddAttemptScore() {
        quizTaker.addAttemptScore(8, 10);
        quizTaker.addAttemptScore(9, 10);
        quizTaker.addAttemptScore(10, 10);

        List<String> scores = quizTaker.getLastTwoScores();
        assertEquals(2, scores.size());
        assertTrue(scores.contains("9/10 (90.0%)"));
        assertTrue(scores.contains("10/10 (100.0%)"));
    }

    @Test
    public void testGetLastTwoScores() {
        quizTaker.addAttemptScore(8, 10);
        quizTaker.addAttemptScore(9, 10);

        List<String> scores = quizTaker.getLastTwoScores();
        assertEquals(2, scores.size());
        assertEquals("8/10 (80.0%)", scores.get(0));
        assertEquals("9/10 (90.0%)", scores.get(1));
    }

    @Test
    public void testGetLastTwoScoresWithNewScore() {
        quizTaker.addAttemptScore(8, 10);
        quizTaker.addAttemptScore(9, 10);
        quizTaker.addAttemptScore(10, 10);

        List<String> scores = quizTaker.getLastTwoScores();
        assertEquals(2, scores.size());
        assertEquals("9/10 (90.0%)", scores.get(0));
        assertEquals("10/10 (100.0%)", scores.get(1));
    }
}
