package ca.unknown.bot.data_access.quiz_me;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Data access for Quiz_Me, allows saving and loading of quizzes, also defines location to store those files
 */
public class JSONQuizMeRepository{

    private static final String QUIZ_DIRECTORY = "src/main/java/ca/unknown/bot/data_access/quiz_me/quizzes";


    /**
     * Constructor for JSONQuizMeRepository, creates file at given location or creates directory if it does not exist
     */
    public JSONQuizMeRepository() {
        File directory = new File(QUIZ_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     *
     * @param quizMe The desired quiz object to be loaded or saved
     * @param filename The name of the quiz so users can look through the different available quizzes
     */
    public void saveQuizMe(QuizMe quizMe, String filename) {
        Gson gson = new Gson();
        String json = gson.toJson(quizMe);

        try (FileWriter writer = new FileWriter(QUIZ_DIRECTORY + "/" + filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
      * @param filename Name of quiz object to be loaded
     * @return QuizMe using file reader
     */
    public QuizMe loadQuizMe(String filename) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(QUIZ_DIRECTORY + "/" + filename)) {
            return gson.fromJson(reader, QuizMe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QuizMe();
    }
}
