package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.QuizMe;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONQuizMeRepository{
    /**
     *
     * @param quizMe The desired quiz object to be loaded or saved
     * @param filename The name of the quiz so users can look through the different available quizzes
     */
    public void saveQuizMe(QuizMe quizMe, String filename) {
        Gson gson = new Gson();
        String json = gson.toJson(quizMe);

        // Checks for exceptions since they are common with file operations
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
      * @param filename Name of quiz object to be loaded
     * @return Quizme using file reader
     */
    public QuizMe loadQuizMe(String filename) {
        Gson gson = new Gson();
    
        try (FileReader reader = new FileReader(filename)) {
            // gson.fromJson converts from the json file we have back to a Java object
            return gson.fromJson(reader, QuizMe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QuizMe();
    }
}