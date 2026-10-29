package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the rank of a town has changed: ranked up, or lost.
 */
public class TownRankChangeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final int oldRank;

    /**
     * Creates the event.
     *
     * @param town the town, with its new rank
     * @param oldRank the rank it had
     */
    public TownRankChangeEvent(@NotNull Town town, int oldRank) {
        this.town = town;
        this.oldRank = oldRank;
    }

    /**
     * @return the town, with its new rank
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the rank it had
     */
    public int getOldRank() {
        return oldRank;
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
