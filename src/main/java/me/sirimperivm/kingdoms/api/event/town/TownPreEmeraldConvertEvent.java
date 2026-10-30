package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before real emeralds are converted into emeralds of a town. Cancelling it, the action does not happen.
 */
public class TownPreEmeraldConvertEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final Player player;
    private final double amount;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param player the player making the action
     * @param amount the emeralds the town receives
     */
    public TownPreEmeraldConvertEvent(@NotNull Town town, @NotNull Player player, double amount) {
        this.town = town;
        this.player = player;
        this.amount = amount;
    }

    /**
     * @return the town
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

    /**
     * @return the emeralds the town receives
     */
    public double getAmount() {
        return amount;
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
