package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a town reaches the next rank. Cancelling it, the action does not happen.
 */
public class TownPreRankUpEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final Player player;
    private final int newRank;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param player the player making the action
     * @param newRank the rank it reaches
     */
    public TownPreRankUpEvent(@NotNull Town town, @NotNull Player player, int newRank) {
        this.town = town;
        this.player = player;
        this.newRank = newRank;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the player making the action
     */
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * @return the rank it reaches
     */
    public int getNewRank() {
        return newRank;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * @return the handlers of the event, used by Bukkit
     */
    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
