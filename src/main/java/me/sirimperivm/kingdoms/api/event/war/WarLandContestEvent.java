package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.LandPosition;
import me.sirimperivm.kingdoms.api.model.War;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the attackers have contested a land of the defender.
 */
public class WarLandContestEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final War war;
    private final LandPosition land;

    /**
     * Creates the event.
     *
     * @param war the war
     * @param land the contested land
     */
    public WarLandContestEvent(@NotNull War war, @NotNull LandPosition land) {
        this.war = war;
        this.land = land;
    }

    /**
     * @return the war
     */
    public @NotNull War getWar() {
        return war;
    }

    /**
     * @return the contested land
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
