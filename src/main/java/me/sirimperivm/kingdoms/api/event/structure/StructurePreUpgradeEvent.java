package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a structure reaches the next level. Cancelling it, the action does not happen.
 */
public class StructurePreUpgradeEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Structure structure;
    private final Player player;
    private final int newLevel;

    /**
     * Creates the event.
     *
     * @param structure the structure
     * @param player the player making the action
     * @param newLevel the level it reaches
     */
    public StructurePreUpgradeEvent(@NotNull Structure structure, @NotNull Player player, int newLevel) {
        this.structure = structure;
        this.player = player;
        this.newLevel = newLevel;
    }

    /**
     * @return the structure
     */
    public @NotNull Structure getStructure() {
        return structure;
    }

    /**
     * @return the player making the action
     */
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * @return the level it reaches
     */
    public int getNewLevel() {
        return newLevel;
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
