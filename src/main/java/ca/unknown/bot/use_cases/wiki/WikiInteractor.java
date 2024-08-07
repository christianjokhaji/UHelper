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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("uhelper-wiki")) {
            String feature = Objects.requireNonNull(event.getOption("feature")).getAsString();

            switch (feature) {
                case "general":
                    event.deferReply().queue();
                    List<EmbedBuilder> general_embeds = new WikiPresenter(event).getGeneralEmbed();
                    jda.addEventListener(new Paginator(event, general_embeds));
                    break;
                case "schedule_reminder":
                    EmbedBuilder schedule_embed =
                            new WikiPresenter(event).getScheduledReminderEmbed();
                    event.getChannel().sendMessageEmbeds(schedule_embed.build()).queue();
                case "timer":
                    EmbedBuilder timer_embed = new WikiPresenter(event).getTimerEmbed();
                    event.getChannel().sendMessageEmbeds(timer_embed.build()).queue();
                    break;
                case "study_helper":
                    EmbedBuilder study_help_embed = new WikiPresenter(event).getStudyHelperEmbed();
                    event.getChannel().sendMessageEmbeds(study_help_embed.build()).queue();
                    break;
                case "mini_games":
                    EmbedBuilder games_embed = new WikiPresenter(event).getMiniGamesEmbed();
                    event.getChannel().sendMessageEmbeds(games_embed.build()).queue();
                case "find_recipe":
                    EmbedBuilder find_recipes_embed =
                            new WikiPresenter(event).getFindRecipesEmbed();
                    event.getChannel().sendMessageEmbeds(find_recipes_embed.build()).queue();
                default:
                    event.reply("This is an invalid command. Please try again!").queue();
                    break;
            }
        }
    }
}
