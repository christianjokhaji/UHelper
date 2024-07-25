package ca.unknown.bot.entities.timer;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class TimerListener extends ListenerAdapter {
    private final Pomodoro timer;
    private ArrayList<User> subscribers = new ArrayList<>();
    private String message;
    /**
     *
     */

    // Constructor for TimerListener
    public TimerListener(Pomodoro timer, ArrayList<User> subscribers) {
        this.timer = timer;
        this.subscribers = subscribers;
        this.message = "";
    }

    public void subscribe(User user) {
        this.subscribers.add(user);
    }

    public void unsubscribe(User user) {
        this.subscribers.remove(user);
    }

    public void activate() {
        Timer update = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                return;
            }
        };
    }

    public void updateMessage(String message) {
        this.message = message;
    }


    public void sendMessageToAll(String message) {
        for (User user : subscribers) {
            sendPrivateMessage(user, "lol");
        };

    }
    public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(content).queueAfter(1, TimeUnit.MINUTES);
        });
    }
}
