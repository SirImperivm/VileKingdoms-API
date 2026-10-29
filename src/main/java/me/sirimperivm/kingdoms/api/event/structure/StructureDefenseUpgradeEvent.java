package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the defenses of a structure have reached the next level.
 */
public class StructureDefenseUpgradeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Structure structure;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param structure the structure, with its new defense level
     * @param player the player making the action
     */
    public StructureDefenseUpgradeEvent(@NotNull Structure structure, @NotNull Player player) {
        this.structure = structure;
        this.player = player;
    }

    /**
     * @return the structure, with its new defense level
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
