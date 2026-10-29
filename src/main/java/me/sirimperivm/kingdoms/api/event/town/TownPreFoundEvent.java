package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.LandPosition;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a player founds a town for their kingdom. Cancelling it, the action does not happen.
 */
public class TownPreFoundEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player founder;
    private final Kingdom kingdom;
    private final String displayName;
    private final LandPosition land;

    /**
     * Creates the event.
     *
     * @param founder the founder, its first leader
     * @param kingdom the kingdom of the town
     * @param displayName the display name chosen
     * @param land the land of the town hall
     */
    public TownPreFoundEvent(@NotNull Player founder, @NotNull Kingdom kingdom, @NotNull String displayName, @NotNull LandPosition land) {
        this.founder = founder;
        this.kingdom = kingdom;
        this.displayName = displayName;
        this.land = land;
    }

    /**
     * @return the founder, its first leader
     */
    public @NotNull Player getFounder() {
        return founder;
    }

    /**
     * @return the kingdom of the town
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return the display name chosen
     */
    public @NotNull String getDisplayName() {
        return displayName;
    }

    /**
     * @return the land of the town hall
     */
    public @NotNull LandPosition getLand() {
        return land;
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
