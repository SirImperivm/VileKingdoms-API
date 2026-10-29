package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.StructureType;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a structure has been picked up: it is not placed anymore.
 */
public class StructurePickupEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID structureId;
    private final StructureType type;
    private final Town town;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param structureId the id of the structure
     * @param type its type
     * @param town the town it belonged to
     * @param player the player making the action
     */
    public StructurePickupEvent(@NotNull UUID structureId, @NotNull StructureType type, @NotNull Town town, @NotNull Player player) {
        this.structureId = structureId;
        this.type = type;
        this.town = town;
        this.player = player;
    }

    /**
     * @return the id of the structure
     */
    public @NotNull UUID getStructureId() {
        return structureId;
    }

    /**
     * @return its type
     */
    public @NotNull StructureType getType() {
        return type;
    }

    /**
     * @return the town it belonged to
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
