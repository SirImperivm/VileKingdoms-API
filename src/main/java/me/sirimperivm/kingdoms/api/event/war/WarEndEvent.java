package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.War;
import me.sirimperivm.kingdoms.api.model.WarOutcome;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a war has ended.
 */
public class WarEndEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final War war;
    private final WarOutcome outcome;
    private final double paid;

    /**
     * Creates the event.
     *
     * @param war the war, with its outcome
     * @param outcome the outcome
     * @param paid the money paid between the banks
     */
    public WarEndEvent(@NotNull War war, @NotNull WarOutcome outcome, double paid) {
        this.war = war;
        this.outcome = outcome;
        this.paid = paid;
    }

    /**
     * @return the war, with its outcome
     */
    public @NotNull War getWar() {
        return war;
    }

    /**
     * @return the outcome
     */
    public @NotNull WarOutcome getOutcome() {
        return outcome;
    }

    /**
     * @return the money paid between the banks
     */
    public double getPaid() {
        return paid;
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
