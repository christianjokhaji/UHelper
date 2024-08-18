package ca.unknown.bot.entities.quiz_me;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

/**
 * Basic entity representing a user's stats taking quizzes
 */
public class QuizTaker {
    private final Map<String, Integer> questionAttempts;
    private final Map<String, Integer> correctAnswers;

    private final String userId;
    private int currentScore;
    private final List<String> attemptScores;


    /**
     * Constructor for QuizTaker
     *
     * @param userId Initializes with users discord id
     */
    public QuizTaker(String userId) {
        this.userId = userId;
        this.questionAttempts = new HashMap<>();
        this.correctAnswers = new HashMap<>();
        this.currentScore = 0;
        this.attemptScores = new LinkedList<>();
    }
    /**
     * Getter for userId
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Getter for current score
     *
     * @return currentScore
     */
    public int getCurrentScore() {
        return currentScore;
    }
    /**
     * Getter for correctAnswers array
     *
     * @return correctAnswers
     */
    public Map<String, Integer> getCorrectAnswers() {
        return correctAnswers;
    }
    /**
     * Getter for questionAttempts array
     *
     * @return questionAttempts
     */
    public Map<String, Integer> getQuestionAttempts() {
        return questionAttempts;
    }
    /**
     * Increment for score whenever user gets a correct answer
     */
    public void incrementScore() {
        currentScore++;
    }
    /**
     * Reset for score whenever user finishes their quiz
     */
    public void resetScore() {
        currentScore = 0;
    }



    /**
     * Records user has made attempt
     *
     * @param question Question corresponding to current answer
     */
    public void recordAttempt(String question) {
        questionAttempts.put(question, questionAttempts.getOrDefault(question, 0) + 1);
    }

    /**
     * Records user was right
     *
     * @param question Question corresponding to current answer
     */
    public void recordCorrectAnswer(String question) {
        correctAnswers.put(question, correctAnswers.getOrDefault(question, 0) + 1);
    }

    /**
     * Creates display text to tell user their grade as a fraction and percentage, as well as showing 2 prior attempts
     *
     * @param score The score the user got
     * @param totalQuestions The total questions in the quiz
     */

    public void addAttemptScore(int score, int totalQuestions) {
        String attemptScore = score + "/" + totalQuestions + " (" + String.format("%.1f",
                ((double) score / totalQuestions) * 100) + "%)";

        if (attemptScores.size() > 1) {
            attemptScores.remove(0);
        }

        attemptScores.add(attemptScore);
    }

    /**
     *
     * @return last two attempt scores
     */

    public List<String> getLastTwoScores() {
        return new LinkedList<>(attemptScores);
    }
}
