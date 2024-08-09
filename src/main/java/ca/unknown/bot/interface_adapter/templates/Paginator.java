package ca.unknown.bot.interface_adapter.templates;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Paginator is a utility class for creating and handling paginated embeds in a Discord bot.
 * It manages pagination buttons and updates the displayed embed based on user interactions.
 * Note: Currently, the first page should be set as a summary page: it has UHelper as author, and
 * UHelper color code for embed color. Plus, only 1 style (simple arrows) is set for buttons.
 * Customizations are available via setters.
 */

public class Paginator extends ListenerAdapter {
    public List<EmbedBuilder> pages;
    public Button firstButton = Button.primary("to_first", "|<").asDisabled();
    public Button prevButton = Button.success("to_prev", "<").asDisabled();
    public Button currButton;
    public Button nextButton = Button.success("to_next", ">");
    public Button lastButton = Button.primary("to_last", ">|");
    public int currPage;
    public List<Button> buttons;
    public SlashCommandInteractionEvent event;

    /**
     * @param event The SlashCommandInteractionEvent that triggered the pagination.
     * @param pages The list of EmbedBuilder instances representing the pages.
     */
    public Paginator(SlashCommandInteractionEvent event, List<EmbedBuilder> pages) {
        this.event = event;
        this.pages = pages;
        this.currPage = 0;
        this.currButton = Button.secondary(
                "curr_button", String.format("%d / %d", currPage, pages.size()-1)
        ).withDisabled(true);
        if (currPage == pages.size() - 1 || pages.isEmpty()) {
            nextButton = nextButton.asDisabled();
            lastButton = lastButton.asDisabled();
        }
        buttons = new ArrayList<>();
        buttons.add(firstButton);
        buttons.add(prevButton);
        buttons.add(currButton);
        buttons.add(nextButton);
        buttons.add(lastButton);

        EmbedBuilder firstPage = pages.get(0);
        firstPage.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(Color.decode("#5E80A2"));

        event.getHook().sendMessageEmbeds(firstPage.build())
                .addActionRow(buttons)
                .setEphemeral(true)
                .queue();
    }

    /**
     * Handles button interactions for pagination.
     *
     * @param e The ButtonInteractionEvent triggered by a button click.
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        e.deferEdit().queue();
        if (pages == null || pages.isEmpty()) {
            e.getHook().sendMessage("No pages available.").setEphemeral(true).queue();
            return;
        }

        String buttonID = e.getComponent().getId();
        assert buttonID != null;
        if (buttonID.equals("to_first")) {
            this.currPage = 0;
        } else if (buttonID.equals("to_prev") && (currPage > 0)) {
            currPage--;
        } else if (buttonID.equals("to_next") && (currPage < pages.size() - 1)) {
            currPage++;
        } else if (buttonID.equals("to_last")) {
            currPage = pages.size() - 1;
        }

        if (currPage == 0) {
            firstButton = firstButton.asDisabled();
            prevButton = prevButton.asDisabled();
            if (pages.size() > 1){
                nextButton = nextButton.asEnabled();
                lastButton = lastButton.asEnabled();
            } else {
                nextButton = nextButton.asDisabled();
                lastButton = lastButton.asDisabled();
            }
        } else if (currPage == pages.size() - 1) {
            nextButton = nextButton.asDisabled();
            lastButton = lastButton.asDisabled();
            if (pages.size() > 1){
                prevButton = prevButton.asEnabled();
                currButton = currButton.asEnabled();
            } else {
                prevButton = prevButton.asDisabled();
                currButton = currButton.asDisabled();
            }
        } else {
            firstButton = firstButton.asEnabled();
            prevButton = prevButton.asEnabled();
            nextButton = nextButton.asEnabled();
            lastButton = lastButton.asEnabled();
        }

        currButton = Button.secondary(
                "curr_button", String.format("%d / %d", this.currPage, pages.size()-1)
        ).withDisabled(true);
        buttons.clear();
        buttons.add(firstButton);
        buttons.add(prevButton);
        buttons.add(currButton);
        buttons.add(nextButton);
        buttons.add(lastButton);

        EmbedBuilder currEmbed = pages.get(currPage);
        event.getHook().editOriginalEmbeds(currEmbed.build()).setActionRow(buttons).queue();
    }
}
