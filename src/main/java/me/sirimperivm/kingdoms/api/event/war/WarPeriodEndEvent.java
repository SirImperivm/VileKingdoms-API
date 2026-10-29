package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a period of war has ended and a period of peace has started.
 */
public class WarPeriodEndEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final boolean permanentPeace;

    /**
     * Creates the event.
     *
     * @param permanentPeace whether the peace lasts until an admin starts a period of war
     */
    public WarPeriodEndEvent(boolean permanentPeace) {
        this.permanentPeace = permanentPeace;
    }

    /**
     * @return whether the peace lasts until an admin starts a period of war
     */
    public boolean isPermanentPeace() {
        return permanentPeace;
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
