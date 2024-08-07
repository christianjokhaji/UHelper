package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


/**
 * Use case interactor for receiving input from the user about which indexed event they want removed from their schedule.
 */
public class ClearEventListener extends ListenerAdapter {
    private final int size;

    private final JDA jda;

    /**
     * The current DAO in use by the program.
     */
    final ScheduledReminderDataAccessInterface scheduleDAO;

    /**
     * Class constructor
     * @param size the number of events in the user's schedule
     * @param scheduleDAO the current data access object in use by the program
     */
    public ClearEventListener(int size, ScheduledReminderDataAccessInterface scheduleDAO, JDA jda){
        this.size = size;
        this.scheduleDAO = scheduleDAO;
        this.jda = jda;
    }

    /**
     * Collects user input for the indexed event which the user wants removed from their schedule. Replies to the user
     * if they selected an invalid index.
     * @param event represents a MessageReceivedEvent
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Message message = event.getMessage();
        String user = message.getAuthor().getName();

        try{
            int index = Integer.parseInt(message.getContentRaw());
            if(index > size || index == 0){
                message.getChannel().sendMessage("Please try again with a valid index.").queue();
            }
            else{
                String confirmationMsg = new ClearEventInteractor(scheduleDAO).execute(user, index);
                message.getChannel().sendMessage("You have been unsubscribed from the following event: " + confirmationMsg).queue();
            }
        } catch(NumberFormatException e){
            message.getChannel().sendMessage("Please try again with a valid index.").queue();
        }
        jda.removeEventListener(this);
    }
}
