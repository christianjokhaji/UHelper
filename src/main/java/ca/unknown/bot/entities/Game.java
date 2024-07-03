package ca.unknown.bot.entities;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * An abstract class representing a Game entity.
 */
public abstract class Game {
    public abstract void startGame(SlashCommandInteractionEvent event);
}