package ca.unknown.bot.app;

import ca.unknown.bot.entities.Assignment;
import java.util.Calendar;
import ca.unknown.bot.entities.EventDate;
import ca.unknown.bot.entities.Exam;

public class Main {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
     *
     * @param args Stores Java command-line arguments
     */
    public static void main(String[] args) {
        EventDate dueDate = new EventDate(2024, 11, 29, 14, 00, 00);
        Assignment test = new Assignment("CSC207", "Phase 1", dueDate.getDate());
        Exam exam = new Exam("CSC207", "Exam Centre", dueDate.getDate());
        System.out.println(exam);
    }
}