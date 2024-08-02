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
        String StudyHelper_description = "1. `Add Question`: Allows a user to add a question, " +
                "answer and optional hint to the current quiz\n" +
                "2. `Reset Notes`: Reset current quiz to be empty\n" +
                "3. `Study`: Allows the user to study the quiz, " +
                "being show a question and being able to display a hint if desired. " +
                "(In Development, user will be able to write their answer and see " +
                "if its correct or not, being given a final score at the end of the quiz)\n" +
                "4. `Save Quiz`: Saves quiz to the bot with a name so the user can use the quiz " +
                "later\n"+
                "5. `Load Quiz`: Load a previously saved quiz.\n" +
                "6. (In Development) `Check Scores`: " +
                "Check scores across multiple study sessions to see if you have been improving.";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Study Help :writing_hand:");
        eb.setAuthor("Dusan");
        eb.setDescription("Help students study by allowing them to create quizzes to practice " +
                        "off\n\n");
        eb.addField("Features", StudyHelper_description, false);
        eb.setColor(Color.green);
        return eb;
    }

    private EmbedBuilder getTimerEmbed(){
        String Timer_description = "1. `Add timer`: Users can configure how much they want to " +
                "spend on work and how much they want to spend on breaks. They can input an " +
                "integer" + "to specify how many times they'd like to repeat. Discord users can " +
                "make their own custom timer that suits their study habits, instead of the " +
                "typical" + "25 minutes of work and 5 minutes of break routine. \n" +
                "2. `Delete timer`: Users can delete a timer preset by giving its name! " +
                "A user can have up to 5 presets.\n" +
                "3. `Start timer`: Starts a timer and " +
                "you can optionally add other Discord users who also want to do something together"+
                " so that they're also notified by the timer\n" +
                "4. `Cancel timer`: Cancels an ongoing timer. " +
                "But, cancelling doesn't involve deactivating it; " +
                "it's rather making it stop notifying you. ";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Min");
        eb.setTitle("Timer :timer:");
        eb.setDescription("A customizable pomodoro timer\n\n");
        eb.addField("Features", Timer_description, false);
        eb.setColor(Color.magenta);
        return eb;
    }

    private EmbedBuilder getRecipeEmbed(){
        String Recipe_description = "`find-recipe`: By entering a food and a number(the number of" +
                " recipes you want), UHelper will help you find related recipes. " +
                "This feature is powered by Edamam API, providing sources for recipes, " +
                "ingredients you need and nutritional information. " +
                "By choosing optional parameters, " +
                "UHelper can generate more specific recipes to suit your needs.";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Cindy / Xinyue");
        eb.setTitle("Recipe :yum:");
        eb.setDescription("Find recipes with ingredients and nutritional info.");
        eb.addField("Feature", Recipe_description, false);
        eb.setColor(Color.orange);
        return eb;
    }

    private List<EmbedBuilder> getGeneralEmbed(){
        List<EmbedBuilder> embeds = new ArrayList<>();
        embeds.add(new EmbedBuilder()
                .setTitle("Welcome to UHelper Wiki! :partying_face:")
                .setAuthor("UHelper Team",
                        null,
                        event.getJDA().getSelfUser().getAvatarUrl())
//                          not working as the sample expected:
//                          https://gist.github.com/zekroTJA/c8ed671204dafbbdf89c36fc3a1827e1
//                        "https://github.com/zekro-archive/DiscordBot/blob/master/" +
//                                ".websrc/zekroBot_Logo_-_round_small.png")
                .setColor(Color.decode("#5E80A2"))
                .addField("", "Thank you for using UHelper!\n\n" +
                        "Feel free to use the buttons below" +
                        " to explore what you can do with UHelper :raised_hands:", false)
                .addBlankField(false)
        );
        embeds.add(getStudyHelperEmbed());
        embeds.add(getTimerEmbed());
        embeds.add(getRecipeEmbed());
        return embeds;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String feature = Objects.requireNonNull(event.getOption("feature")).getAsString();

        switch (feature) {
            case "general":
                WikiInteractor interactor = new WikiInteractor(jda);
                interactor.setEvent(event);
                event.deferReply().queue();
                jda.addEventListener(new Paginator(event, interactor.getGeneralEmbed()));
                break;
            case "study_helper":
                EmbedBuilder study_help_eb = getStudyHelperEmbed();
                event.getChannel().sendMessageEmbeds(study_help_eb.build()).queue();
                break;
            case "timer":
                EmbedBuilder timer_eb = getTimerEmbed();
                event.getChannel().sendMessageEmbeds(timer_eb.build()).queue();
                break;
            case "find_recipe":
                EmbedBuilder recipe_eb = getRecipeEmbed();
                event.getChannel().sendMessageEmbeds(recipe_eb.build()).queue();
            default:
                event.reply("This is an invalid command. Please try again!").queue();
                break;
        }
    }
}
