package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before the defenders of a structure appear to attack an enemy. Cancelling it, the action does not happen.
 */
public class DefenderPreSpawnEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Structure structure;
    private final Player target;
    private final int count;

    /**
     * Creates the event.
     *
     * @param structure the defended structure
     * @param target the enemy they attack
     * @param count how many defenders appear
     */
    public DefenderPreSpawnEvent(@NotNull Structure structure, @NotNull Player target, int count) {
        this.structure = structure;
        this.target = target;
        this.count = count;
    }

    /**
     * @return the defended structure
     */
    public @NotNull Structure getStructure() {
        return structure;
    }

    /**
     * @return the enemy they attack
     */
    public @NotNull Player getTarget() {
        return target;
    }

    /**
     * @return how many defenders appear
     */
    public int getCount() {
        return count;
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
