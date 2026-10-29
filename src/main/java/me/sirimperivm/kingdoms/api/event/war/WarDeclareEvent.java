package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.War;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a war has been declared and started.
 */
public class WarDeclareEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final War war;

    /**
     * Creates the event.
     *
     * @param war the war
     */
    public WarDeclareEvent(@NotNull War war) {
        this.war = war;
    }

    /**
     * @return the war
     */
    public @NotNull War getWar() {
        return war;
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
