package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a player creates a kingdom. Cancelling it, the action does not happen.
 */
public class KingdomPreCreateEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player creator;
    private final String displayName;

    /**
     * Creates the event.
     *
     * @param creator the player creating the kingdom, its king
     * @param displayName the display name chosen
     */
    public KingdomPreCreateEvent(@NotNull Player creator, @NotNull String displayName) {
        this.creator = creator;
        this.displayName = displayName;
    }

    /**
     * @return the player creating the kingdom, its king
     */
    public @NotNull Player getCreator() {
        return creator;
    }

    /**
     * @return the display name chosen
     */
    public @NotNull String getDisplayName() {
        return displayName;
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
