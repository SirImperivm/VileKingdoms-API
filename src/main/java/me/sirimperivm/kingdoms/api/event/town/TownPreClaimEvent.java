package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.LandPosition;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called before a land is claimed for a town, by a member or by an admin. Cancelling it, the action does not happen.
 */
public class TownPreClaimEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final LandPosition land;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param land the land
     * @param player who claims it
     */
    public TownPreClaimEvent(@NotNull Town town, @NotNull LandPosition land, @Nullable Player player) {
        this.town = town;
        this.land = land;
        this.player = player;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the land
     */
    public @NotNull LandPosition getLand() {
        return land;
    }

    /**
     * @return who claims it
     */
    public @Nullable Player getPlayer() {
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
