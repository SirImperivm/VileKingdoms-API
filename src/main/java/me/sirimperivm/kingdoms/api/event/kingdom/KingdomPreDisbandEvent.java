package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called before a kingdom is disbanded by its king or destroyed by an admin. Cancelling it, the action does not happen.
 */
public class KingdomPreDisbandEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final CommandSender actor;
    private final boolean admin;

    /**
     * Creates the event.
     *
     * @param kingdom the kingdom
     * @param actor who disbands it
     * @param admin whether an admin destroys it
     */
    public KingdomPreDisbandEvent(@NotNull Kingdom kingdom, @Nullable CommandSender actor, boolean admin) {
        this.kingdom = kingdom;
        this.actor = actor;
        this.admin = admin;
    }

    /**
     * @return the kingdom
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return who disbands it
     */
    public @Nullable CommandSender getActor() {
        return actor;
    }

    /**
     * @return whether an admin destroys it
     */
    public boolean isAdmin() {
        return admin;
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
