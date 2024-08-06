package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClearEventListener extends ListenerAdapter {
    /**
     * The number of events in the interacting user's schedule.
      */
    private int size;

    private JDA jda;

    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;

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
