package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a town has been founded.
 */
public class TownFoundEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final Player founder;

    /**
     * Creates the event.
     *
     * @param town the new town
     * @param founder its first leader
     */
    public TownFoundEvent(@NotNull Town town, @NotNull Player founder) {
        this.town = town;
        this.founder = founder;
    }

    /**
     * @return the new town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return its first leader
     */
    public @NotNull Player getFounder() {
        return founder;
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
