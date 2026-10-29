package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a town now belongs to another kingdom, conquered in a war.
 */
public class TownKingdomChangeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final UUID oldKingdomId;
    private final Kingdom newKingdom;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param oldKingdomId the kingdom it belonged to
     * @param newKingdom its new kingdom
     */
    public TownKingdomChangeEvent(@NotNull Town town, @NotNull UUID oldKingdomId, @NotNull Kingdom newKingdom) {
        this.town = town;
        this.oldKingdomId = oldKingdomId;
        this.newKingdom = newKingdom;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the kingdom it belonged to
     */
    public @NotNull UUID getOldKingdomId() {
        return oldKingdomId;
    }

    /**
     * @return its new kingdom
     */
    public @NotNull Kingdom getNewKingdom() {
        return newKingdom;
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
