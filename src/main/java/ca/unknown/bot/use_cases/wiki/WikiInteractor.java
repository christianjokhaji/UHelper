package ca.unknown.bot.use_cases.wiki;

import ca.unknown.bot.interface_adapter.templates.Paginator;
import ca.unknown.bot.interface_adapter.wiki.WikiPresenter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

/**
 * The WikiInteractor class handles slash command interactions for the UHelper bot's wiki features.
 * It processes the input option, process the appropriate data with WikiPresenter to
 * format the data for intuitive display.
 */
public class WikiInteractor extends ListenerAdapter {
    private final JDA jda;
    /**
     * @param jda creates an alias of jda to make the paginator event listener to work
     */
    public WikiInteractor(JDA jda){
        this.jda = jda;
    }

    /**
     * Handles slash command interactions. Depending on the command feature,
     * it calls the WikiPresenter to generate appropriate output and sends it as a response.
     *
     * @param event the slash command interaction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("uhelper-wiki")) {
            String feature = Objects.requireNonNull(event.getOption("feature")).getAsString();
            event.deferReply().queue();

            switch (feature) {
                case "general":
                    List<EmbedBuilder> general_embeds = new WikiPresenter(event).getGeneralEmbed();
                    jda.addEventListener(new Paginator(event, general_embeds));
                    break;
                case "scheduled_reminders":
                    EmbedBuilder schedule_embed =
                            new WikiPresenter(event).getScheduledRemindersEmbed();
                    event.getHook().sendMessageEmbeds(schedule_embed.build()).queue();
                    break;
                case "timer":
                    EmbedBuilder timer_embed = new WikiPresenter(event).getTimerEmbed();
                    event.getHook().sendMessageEmbeds(timer_embed.build()).queue();
                    break;
                case "study_helper":
                    EmbedBuilder study_help_embed = new WikiPresenter(event).getStudyHelperEmbed();
                    event.getHook().sendMessageEmbeds(study_help_embed.build()).queue();
                    break;
                case "mini_games":
                    EmbedBuilder games_embed = new WikiPresenter(event).getMiniGamesEmbed();
                    event.getHook().sendMessageEmbeds(games_embed.build()).queue();
                    break;
                case "find_recipes":
                    EmbedBuilder find_recipes_embed =
                            new WikiPresenter(event).getFindRecipesEmbed();
                    event.getHook().sendMessageEmbeds(find_recipes_embed.build()).queue();
                    break;
                default:
                    event.getHook().sendMessage(
                            "This is an invalid command. Please try again!"
                    ).queue();
                    break;
            }
        }
    }
}
