package ca.unknown.bot.entities.quiz_me;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import ca.unknown.bot.use_cases.utils.ModalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Basic entity for quiz_me methods
 */
public class QuizMe {
    private final Map<String, String> notes;
    private final Map<String, String> hints;
    private final List<String> questionsOrder;
    private final Map<String, QuizTaker> quizTakers;


    /**
     * Constructor for quiz_me entity with new values
     */
    public QuizMe() {
        this.notes = new HashMap<>();
        this.hints = new HashMap<>();
        this.questionsOrder = new ArrayList<>();
        this.quizTakers = new HashMap<>();
    }

    /**
     * Getter for notes
     *
     * @return notes
     */
    public Map<String, String> getNotes() {
        return notes;
    }

    /**
     * Getter for hints
     *
     * @return hints
     */
    public Map<String, String> getHints() {
        return hints;
    }

    /**
     * Getter for questions order
     *
     * @return questionsOrder
     */
    public List<String> getQuestionsOrder() { return questionsOrder; }




    /**
     * Resets all questions and answers, effectively clearing the quiz.
     *
     * @param event The SlashCommandInteractionEvent that triggered this method.
     */
    public void resetNotes(@NotNull SlashCommandInteractionEvent event) {
        notes.clear();
        hints.clear();
        questionsOrder.clear();
        event.reply("Notes Reset!").queue();
    }

    /**
     * Adds a new question, its answer, and a hint.
     *
     * @param question The question to be added.
     * @param answer The answer to be added.
     * @param hint The hint to be added .
     */
    public void addQuestionAndAnswer(String question, String answer, String hint) {
        notes.put(question, answer);
        hints.put(question, hint);
        if (!questionsOrder.contains(question)) {
            questionsOrder.add(question);
            System.out.println("Successfully added question " );
        } else {
            System.out.println("Question already exists");
        }

    }

    /**
     * Starts a study session by asking the first question then continuing when user presses button.
     *
     * @param event The SlashCommandInteractionEvent triggering this method.
     */
    public void study(SlashCommandInteractionEvent event) {
        if (notes.isEmpty()) {
            event.reply("There are no questions to study.").queue();
            return;
        }

        String firstQuestion = questionsOrder.get(0);
        Button answerButton = Button.primary("answer_" + firstQuestion + "_0", "Answer");
        Button hintButton = Button.secondary("hint_" + firstQuestion + "_0", "Show Hint");
        event.reply("Question: " + firstQuestion)
                .addActionRow(answerButton, hintButton)
                .queue();
    }



    /**
     * Shows the hint for a question.
     *
     * @param event The ButtonInteractionEvent triggering this method.
     * @param question The question for which to show the hint.
     * @param currentIndex The index of the current question in the list.
     */

    public void showHint(ButtonInteractionEvent event, String question, int currentIndex) {

        String hint = hints.get(question);
        if (hint != null && !hint.isEmpty()) event.reply("Hint: " + hint).queue();
        else {
            event.reply("No hint available for this question.").queue();
        }
    }



    /**
     * Allows the user to answer a question by opening a modal dialog.
     *
     * @param event The ButtonInteractionEvent triggering this method.
     * @param question The question to be answered.
     * @param currentIndex The index of the current question in the list.
     */

    public void answerQuestion(ButtonInteractionEvent event, String question, int currentIndex) {
        event.replyModal(ModalUtils.createAnswerQuestionModal(question, currentIndex)).queue();
    }

    /**
     * Checks the user's answer against the correct answer and notifies the user if they got it right
     *
     * @param event The ModalInteractionEvent triggering this method.
     * @param question The question that was answered.
     * @param currentIndex The index of the current question in the list.
     * @param userAnswer The user's answer.
     * @param userId The ID of the user who answered.
     */

    public void checkAnswer(ModalInteractionEvent event,
                            String question, int currentIndex, String userAnswer, String userId) {
        String correctAnswer = notes.get(question);
        QuizTaker quizTaker = quizTakers.computeIfAbsent(userId, QuizTaker::new);
        quizTaker.recordAttempt(question);

        if (correctAnswer != null && correctAnswer.equalsIgnoreCase(userAnswer.trim())) {
            quizTaker.recordCorrectAnswer(question);
            quizTaker.incrementScore();
            event.reply("Correct! The answer is: " + correctAnswer)
                    .addActionRow(Button.success("next_" + currentIndex, "Next Question"))
                    .queue();
        } else {
            event.reply("Incorrect. The correct answer is: " + correctAnswer)
                    .addActionRow(Button.danger("next_" + currentIndex, "Next Question"))
                    .queue();
        }
    }

    /**
     * Shows the next question in the quiz, or displays the final score if all questions are answered.
     *
     * @param event The ButtonInteractionEvent triggering this method.
     * @param currentIndex The index of the current question in the list.
     * @param userId The ID of the user who is taking the quiz.
     */

    public void showNextQuestion(ButtonInteractionEvent event, int currentIndex, String userId) {
        int nextIndex = currentIndex + 1;
        QuizTaker quizTaker = quizTakers.computeIfAbsent(userId, QuizTaker::new); // Ensure the quizTaker is retrieved

        if (nextIndex < questionsOrder.size()) {
            String nextQuestion = questionsOrder.get(nextIndex);
            Button nextAnswerButton = Button.primary("answer_" + nextQuestion + "_" + nextIndex, "Answer");
            Button nextHintButton = Button.secondary("hint_" + nextQuestion + "_" + nextIndex, "Show Hint");
            event.getChannel().sendMessage("Question: " + nextQuestion)
                    .setActionRow(nextAnswerButton, nextHintButton)
                    .queue();
        } else {
            int score = quizTaker.getCurrentScore();
            int totalQuestions = questionsOrder.size();
            String scoreMessage = getScoreMessage(score, totalQuestions, quizTaker);
            quizTaker.addAttemptScore(score, totalQuestions);
            quizTaker.resetScore();

            event.reply(scoreMessage).queue();
        }
    }

    /**
     * Generates a message with the user's score and previous scores.
     *
     * @param score The user's current score.
     * @param totalQuestions The total number of questions in the quiz.
     * @param quizTaker The QuizTaker object for the user.
     * @return A formatted score message.
     */

    private static @NotNull String getScoreMessage(int score, int totalQuestions, QuizTaker quizTaker) {
        double percentage = ((double) score / totalQuestions) * 100;
        StringBuilder scoreMessage = new StringBuilder("Quiz completed! Your score: " + score + "/" + totalQuestions +
                " (" + String.format("%.1f", percentage) + "%)");

        List<String> previousScores = quizTaker.getLastTwoScores();
        if (!previousScores.isEmpty()) {
            scoreMessage.append("\n\nPrevious Scores:");
            for (String prevScore : previousScores) {
                scoreMessage.append("\n- ").append(prevScore);
            }
        }
        return scoreMessage.toString();
    }
}


