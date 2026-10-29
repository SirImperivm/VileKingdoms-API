package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called before a town is released by its members or by an admin. Cancelling it, the action does not happen.
 */
public class TownPreReleaseEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final CommandSender actor;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param actor who releases it
     */
    public TownPreReleaseEvent(@NotNull Town town, @Nullable CommandSender actor) {
        this.town = town;
        this.actor = actor;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return who releases it
     */
    public @Nullable CommandSender getActor() {
        return actor;
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
