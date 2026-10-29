package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Structure;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a structure has reached the next level.
 */
public class StructureUpgradeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Structure structure;
    private final int oldLevel;

    /**
     * Creates the event.
     *
     * @param structure the structure, with its new level
     * @param oldLevel the level it had
     */
    public StructureUpgradeEvent(@NotNull Structure structure, int oldLevel) {
        this.structure = structure;
        this.oldLevel = oldLevel;
    }

    /**
     * @return the structure, with its new level
     */
    public @NotNull Structure getStructure() {
        return structure;
    }

    /**
     * @return the level it had
     */
    public int getOldLevel() {
        return oldLevel;
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
