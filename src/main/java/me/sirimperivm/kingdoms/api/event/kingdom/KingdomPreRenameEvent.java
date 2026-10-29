package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before the king renames the kingdom. Cancelling it, the action does not happen.
 */
public class KingdomPreRenameEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final Player player;
    private final String newDisplayName;

    /**
     * Creates the event.
     *
     * @param kingdom the kingdom
     * @param player the player making the action
     * @param newDisplayName the new display name
     */
    public KingdomPreRenameEvent(@NotNull Kingdom kingdom, @NotNull Player player, @NotNull String newDisplayName) {
        this.kingdom = kingdom;
        this.player = player;
        this.newDisplayName = newDisplayName;
    }

    /**
     * @return the kingdom
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return the player making the action
     */
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * @return the new display name
     */
    public @NotNull String getNewDisplayName() {
        return newDisplayName;
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
