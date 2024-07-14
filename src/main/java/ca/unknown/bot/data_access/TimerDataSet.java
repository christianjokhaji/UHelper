package ca.unknown.bot.data_access;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.ArrayList;

public class TimerDataSet {
    private HashMap timerData;
    /**
     * TimerDataSet is an input data structure that is designed to serialize the timer and the user
     * who created it effectively. GSON is used to store data in the JSON format.
     * </p>
     * How are Discord users and Pomodoro instances are stored? The format:
     * {User1: {timer1_name: {"breakTime":2.0,"iteration":1,"workTime":3.0}, timer2_name:
     *      *     {"breakTime":9.0,"iteration":100,"workTime":3.14}
     *      * }, User2: ...}
     *
     * <p>
     * @param user: the one who used /timer_create to make a Pomodoro instance.
     * @param timer: the pomodoro instance that the user created and want to be saved
     */

    // The constructor of
    public TimerDataSet(User user, Pomodoro timer){
        HashMap timerData = new HashMap();
//        try {
//            if (timerData)
//        }
    }




}
