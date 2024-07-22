package ca.unknown.bot.app;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDAO;
import ca.unknown.bot.entities.schedule_reminder.EventDate;

import java.sql.SQLOutput;
import java.util.*;

public class Main2 {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
     *
     * @param args Stores Java command-line arguments
     */
    public static void main(String[] args) {
        ScheduledReminderDAO dao = new ScheduledReminderDAO();
        dao.loadRepo("schedule_repository");
        //System.out.println(dao.emptyCache("schedule_repository"));

    }
}