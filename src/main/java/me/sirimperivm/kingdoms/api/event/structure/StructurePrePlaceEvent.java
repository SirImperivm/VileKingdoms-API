package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.StructureType;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a player places a structure in a claim of their town. Cancelling it, the action does not happen.
 */
public class StructurePrePlaceEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final StructureType type;
    private final int level;
    private final Location location;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param type the type of the structure
     * @param level its level
     * @param location the lowest corner of the structure at the ground
     * @param player the player making the action
     */
    public StructurePrePlaceEvent(@NotNull Town town, @NotNull StructureType type, int level, @NotNull Location location, @NotNull Player player) {
        this.town = town;
        this.type = type;
        this.level = level;
        this.location = location;
        this.player = player;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the type of the structure
     */
    public @NotNull StructureType getType() {
        return type;
    }

    /**
     * @return its level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the lowest corner of the structure at the ground
     */
    public @NotNull Location getLocation() {
        return location;
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
