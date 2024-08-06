package ca.unknown.bot.interface_adapter.templates;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Paginator is a utility class for creating and handling paginated embeds in a Discord bot.
 * It manages pagination buttons and updates the displayed embed based on user interactions.
 * Currently, only 1 style of button groups are set. However, customization of button groups are
 * also available via setters in the future.
 */

public class Paginator extends ListenerAdapter {
    public List<EmbedBuilder> pages;
    public Button firstButton;
    public Button prevButton;
    public Button currButton;
    public Button nextButton;
    public Button lastButton;
    public int currPage;
    public List<Button> buttons;
    public SlashCommandInteractionEvent event;

    /**
     * Constructs a new Paginator instance.
     *
     * @param event The SlashCommandInteractionEvent that triggered the pagination.
     * @param pages The list of EmbedBuilder instances representing the pages.
     */
    public Paginator(SlashCommandInteractionEvent event, List<EmbedBuilder> pages) {
        this.event = event;
        this.pages = pages;
        this.currPage = 0;
        // initialize buttons for pagination
        this.firstButton = Button.primary("to_first", "|<").withDisabled(currPage == 0);
        this.prevButton = Button.success("to_prev", "<").withDisabled(currPage == 0);
        this.currButton = Button.secondary(
                "curr_button", String.format("%d / %d", currPage, pages.size()-1)
        ).withDisabled(true);
        this.nextButton = Button.success("to_next", ">").withDisabled(currPage == pages.size() - 1);
        this.lastButton = Button.primary("to_last", ">|").withDisabled(currPage == pages.size() - 1);
        buttons = new ArrayList<>();
        buttons.add(firstButton);
        buttons.add(prevButton);
        buttons.add(currButton);
        buttons.add(nextButton);
        buttons.add(lastButton);

        // add UHelper Team to the first page of the event
        EmbedBuilder firstPage = pages.get(0);
        firstPage.setAuthor("UHelper Team",
                null,
                event.getJDA().getSelfUser().getAvatarUrl());

        event.getHook().sendMessageEmbeds(firstPage.build())
                .addActionRow(buttons)
                .setEphemeral(true)
                .queue();
    }

    // change buttons for pagination via setters
    public void setFirstButton(Button firstButton) {
        this.firstButton = firstButton;
    }

    public void setPrevButton(Button prevButton) {
        this.prevButton = prevButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

    public void setLastButton(Button lastButton) {
        this.lastButton = lastButton;
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
