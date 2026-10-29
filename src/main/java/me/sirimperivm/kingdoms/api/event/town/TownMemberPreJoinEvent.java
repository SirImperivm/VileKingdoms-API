package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a player joins a town. Cancelling it, the action does not happen.
 */
public class TownMemberPreJoinEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param player the player joining it
     */
    public TownMemberPreJoinEvent(@NotNull Town town, @NotNull Player player) {
        this.town = town;
        this.player = player;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the player joining it
     */
    public @NotNull Player getPlayer() {
        return player;
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
