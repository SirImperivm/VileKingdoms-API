package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a period of war has started: the wars can be declared.
 */
public class WarPeriodStartEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final long endsAt;

    /**
     * Creates the event.
     *
     * @param endsAt when the period ends, in epoch milliseconds
     */
    public WarPeriodStartEvent(long endsAt) {
        this.endsAt = endsAt;
    }

    /**
     * @return when the period ends, in epoch milliseconds
     */
    public long getEndsAt() {
        return endsAt;
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
