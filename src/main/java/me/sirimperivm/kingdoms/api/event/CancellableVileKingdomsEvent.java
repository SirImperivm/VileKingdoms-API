package me.sirimperivm.kingdoms.api.event;

import org.bukkit.event.Cancellable;

/**
 * Event called before an action of VileKingdoms: cancelling it, the action does not happen. Tell the player why,
 * since VileKingdoms only stops.
 */
public abstract class CancellableVileKingdomsEvent extends VileKingdomsEvent implements Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
