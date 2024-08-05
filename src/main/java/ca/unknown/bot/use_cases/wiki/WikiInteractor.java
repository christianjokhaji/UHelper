package ca.unknown.bot.use_cases.wiki;

import ca.unknown.bot.interface_adapter.templates.Paginator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WikiInteractor extends ListenerAdapter {
    private final JDA jda;
    private SlashCommandInteractionEvent event;

    public WikiInteractor(JDA jda){
        // This allows the event listener to work
        this.jda = jda;
    }
    public void setEvent(SlashCommandInteractionEvent event){
        this.event = event;
    }

    private EmbedBuilder getStudyHelperEmbed(){
        String StudyHelper_features =
                "Here are a few options you can choose with `/study-help`:\n" +
                "- **Add Question**: Allows a user to add a question, " +
                "answer and optional hint to the current quiz\n" +
                "- **Reset Notes**: Reset current quiz to be empty\n" +
                "- **Study**: Allows the user to study the quiz, " +
                "being show a question and being able to display a hint if desired. " +
                "(In Development, user will be able to write their answer and see " +
                "if its correct or not, being given a final score at the end of the quiz)\n" +
                "- **Save Quiz**: Saves quiz to the bot with a name so the user can use the quiz " +
                "later\n"+
                "- **Load Quiz**: Load a previously saved quiz.\n" +
                "- (In Development) **Check Scores**: " +
                "Check scores across multiple study sessions to see if you have been improving.";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("Study Help :books:");
        eb.setDescription("Help students study by allowing them to create quizzes to practice " +
                        "off\n\n");
        eb.addField("Features", StudyHelper_features, false);
        eb.setColor(Color.green);
        eb.setFooter("Created by Dusan");
        return eb;
    }

    private EmbedBuilder getTimerEmbed(){
        String Timer_features = "-`timer-add`: Users can configure how much they want to " +
                "spend on work and how much they want to spend on breaks. They can input an " +
                "integer" + "to specify how many times they'd like to repeat. Discord users can " +
                "make their own custom timer that suits their study habits, instead of the " +
                "typical" + "25 minutes of work and 5 minutes of break routine. \n" +
                "-`/timer-delete`: Users can delete a timer preset by giving its name! " +
                "A user can have up to 5 presets.\n" +
                "-`timer-start`: Starts a timer and " +
                "you can optionally add other Discord users who also want to do something together"+
                " so that they're also notified by the timer\n" +
                "-`timer-cancel`: Cancels an ongoing timer. " +
                "But, cancelling doesn't involve deactivating it; " +
                "it's rather making it stop notifying you. ";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("Timer :timer:");
        eb.setDescription("A customizable pomodoro timer\n\n");
        eb.addField("Features", Timer_features, false);
        eb.setColor(Color.decode("#ED73FA"));
        eb.setFooter("Created by Min");
        return eb;
    }

    private EmbedBuilder getFindRecipeEmbed(){
        String Recipe_features =
                "`/find-recipe`: By entering a food and the number of recipes you want, " +
                "UHelper will find related recipes for you, along with source recipes, " +
                "required ingredients, and nutritional information, " +
                "all powered by [Edmaman API](https://www.edamam.com/). " +
                "Plus, by adding optional parameters, you " +
                "can get more specific recipes suited to your needs :scroll:";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("Find Recipe :yum:");
        eb.setDescription("Find recipes with ingredients and nutritional info.\n\n");
        eb.addField("Feature", Recipe_features, false);
        eb.setColor(Color.orange);
        eb.setFooter("Created by Cindy(Xinyue)");
        return eb;
    }

    private EmbedBuilder getMiniGamesEmbed(){
        String minigames_features = "Whenever you have a break or free time, " +
                "call on one of the following commands to start it:\n" +
                "- `/rock-paper-scissors` **your_choice** Starts a game of rock paper scissors.\n" +
                "- `/trivia` Starts a game of true/false trivia.\n" +
                "- `/8ball` Receive a Magic 8Ball reading.";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("Mini Games :video_game:");
        eb.addField("Feature", minigames_features, false);
        eb.setColor(Color.cyan);
        eb.setFooter("Created by Christian");
        return eb;
    }

    private EmbedBuilder getScheduledReminderEmbed(){
        String scheduledReminder_description = "UHelper can schedule any ongoing exams, " +
                "assignments, or general events for you and send you reminders " +
                "via direct message 24 hours before your event, or an hour before the event " +
                "if it was scheduled less than a day before. " +
                "It will also automatically remove events from your schedule once they have " +
                "passed.\n\n";
        String scheduledReminder_features1 = "- `/schedule_event`: Enter the name of your event, " +
                "the date in YYYY MM DD HR MIN format and " +
                "UHelper will add this event to your schedule! " +
                "You cannot schedule events in the past or duplicate events " +
                "(ie. events with the exact same name and time).\n" +
                "- `/schedule_exam`: Enter the course code of your exam, the location of your " +
                "exam, the date in YYYY MM DD HR MIN format and UHelper will add this exam to " +
                "your schedule! You cannot schedule exams in the past or duplicate exams " +
                "(ie. exams at the same time).";
        String scheduledReminder_features2 =
                "- `/schedule_assignment`: Enter the course code of the class that the assignment "+
                "is from, the name of the assignment, the due date in YYYY MM DD HR MIN format " +
                "and UHelper will add this assignment to your schedule! " +
                "You cannot schedule assignments in the past or duplicate assignments " +
                "(ie. assignments with the same course code AND the same name).\n" +
                "- `/current_schedule`: Displays the user's ongoing scheduled events.\n" +
                "- `/clear_event`: Allows the user to remove a specific event from their schedule" +
                ". UHelper will display your current schedule in an indexed list and " +
                "allow you to choose which indexed event you want to remove. " +
                "You will also be unsubscribed from that event's reminder alert.\n" +
                "- `/clear_schedule`: Clears all ongoing events from the user's schedule and " +
                "unsubscribes them from all event reminders.";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());
        eb.setTitle("Schedule Reminder :calendar:");
        eb.setDescription(scheduledReminder_description);
        eb.addField("Features", scheduledReminder_features1, false);
        eb.addField("", scheduledReminder_features2, false);
        eb.setColor(Color.pink);
        eb.setFooter("Created by Aria");
        return eb;
    }

    private List<EmbedBuilder> getGeneralEmbed(){
        List<EmbedBuilder> embeds = new ArrayList<>();
        embeds.add(new EmbedBuilder()
                .setTitle("Welcome to UHelper Wiki! :partying_face:")
                .setAuthor("UHelper Team",
                        null,
                        event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(Color.decode("#5E80A2"))
                .addField("", "Thank you for using UHelper!\n\n" +
                        "Feel free to use the buttons below" +
                        " to explore what you can do with UHelper :raised_hands:", false)
                .addBlankField(false)
        );
        embeds.add(getScheduledReminderEmbed());
        embeds.add(getTimerEmbed());
        embeds.add(getStudyHelperEmbed());
        embeds.add(getMiniGamesEmbed());
        embeds.add(getFindRecipeEmbed());
        return embeds;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("uhelper-wiki")) {
            String feature = Objects.requireNonNull(event.getOption("feature")).getAsString();

            switch (feature) {
                case "general":
                    WikiInteractor interactor = new WikiInteractor(jda);
                    interactor.setEvent(event);
                    event.deferReply().queue();
                    jda.addEventListener(new Paginator(event, interactor.getGeneralEmbed()));
                    break;
                case "schedule_reminder":
                    EmbedBuilder schedule_eb = getFindRecipeEmbed();
                    event.getChannel().sendMessageEmbeds(schedule_eb.build()).queue();
                case "timer":
                    EmbedBuilder timer_eb = getTimerEmbed();
                    event.getChannel().sendMessageEmbeds(timer_eb.build()).queue();
                    break;
                case "study_helper":
                    EmbedBuilder study_help_eb = getStudyHelperEmbed();
                    event.getChannel().sendMessageEmbeds(study_help_eb.build()).queue();
                    break;
                case "mini_games":
                    EmbedBuilder games_eb = getFindRecipeEmbed();
                    event.getChannel().sendMessageEmbeds(games_eb.build()).queue();
                case "find_recipe":
                    EmbedBuilder recipe_eb = getFindRecipeEmbed();
                    event.getChannel().sendMessageEmbeds(recipe_eb.build()).queue();
                default:
                    event.reply("This is an invalid command. Please try again!").queue();
                    break;
            }
        }
    }
}
