package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a kingdom has been created.
 */
public class KingdomCreateEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final Player creator;

    /**
     * Creates the event.
     *
     * @param kingdom the new kingdom
     * @param creator its king
     */
    public KingdomCreateEvent(@NotNull Kingdom kingdom, @NotNull Player creator) {
        this.kingdom = kingdom;
        this.creator = creator;
    }

    /**
     * @return the new kingdom
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return its king
     */
    public @NotNull Player getCreator() {
        return creator;
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
