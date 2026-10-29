package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a town declares war on a town of an enemy kingdom. Cancelling it, the action does not happen.
 */
public class WarPreDeclareEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town attacker;
    private final Town defender;
    private final Player declarer;
    private final double stake;

    /**
     * Creates the event.
     *
     * @param attacker the attacking town
     * @param defender the defending town
     * @param declarer the player declaring the war
     * @param stake the stake of the war
     */
    public WarPreDeclareEvent(@NotNull Town attacker, @NotNull Town defender, @NotNull Player declarer, double stake) {
        this.attacker = attacker;
        this.defender = defender;
        this.declarer = declarer;
        this.stake = stake;
    }

    /**
     * @return the attacking town
     */
    public @NotNull Town getAttacker() {
        return attacker;
    }

    /**
     * @return the defending town
     */
    public @NotNull Town getDefender() {
        return defender;
    }

    /**
     * @return the player declaring the war
     */
    public @NotNull Player getDeclarer() {
        return declarer;
    }

    /**
     * @return the stake of the war
     */
    public double getStake() {
        return stake;
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
