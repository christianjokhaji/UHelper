package ca.unknown.bot.use_cases;

import ca.unknown.bot.data_access.JSONQuizMeRepository;
import ca.unknown.bot.entities.QuizMe;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoadNotesInteractor {

    private final JSONQuizMeRepository repository;
    private final QuizMe quizMe;

    public LoadNotesInteractor(QuizMe quizMe) {
        this.repository = new JSONQuizMeRepository();
        this.quizMe = quizMe;
    }

    /**
     * Loads notes from a specified file.
     *
     * @param filename the name of the file to load notes from.
     * @param event    represents a MessageReceived event.
     */
    public void loadNotesFromFile(String filename, MessageReceivedEvent event) {
        QuizMe loadedQuizMe = repository.loadQuizMe(filename);
        quizMe.getNotes().clear();
        quizMe.getNotes().putAll(loadedQuizMe.getNotes());
        quizMe.getHints().clear();
        quizMe.getHints().putAll(loadedQuizMe.getHints());
        quizMe.getQuestionsOrder().clear();
        quizMe.getQuestionsOrder().addAll(loadedQuizMe.getQuestionsOrder());

        System.out.println("Loaded QuizMe: " + quizMe); // Debug print
        System.out.println("Questions: " + quizMe.getQuestionsOrder().size()); // Debug print

        if (quizMe.getQuestionsOrder().isEmpty()) {
            event.getChannel().sendMessage("There are no questions to study.").queue();
        } else {
            event.getChannel().sendMessage("Notes loaded from " + filename).queue();
            // Handle further logic to utilize the loaded questions...
        }
    }

    /**
     * Displays saved quizzes to the user to choose from.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    public void displaySavedQuizzes(SlashCommandInteractionEvent event) {
        // Implement logic to display saved quizzes
        event.reply("Please provide the name of the quiz file to load (e.g., quiz.json)").queue();
    }
}
