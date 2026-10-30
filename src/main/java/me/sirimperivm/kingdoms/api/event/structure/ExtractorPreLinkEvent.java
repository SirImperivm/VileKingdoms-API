package me.sirimperivm.kingdoms.api.event.structure;

import me.sirimperivm.kingdoms.api.event.CancellableVileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Structure;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before an extractor is linked to a collector. Cancelling it, the action does not happen.
 */
public class ExtractorPreLinkEvent extends CancellableVileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Structure extractor;
    private final Structure collector;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param extractor the extractor
     * @param collector the collector
     * @param player the player making the action
     */
    public ExtractorPreLinkEvent(@NotNull Structure extractor, @NotNull Structure collector, @NotNull Player player) {
        this.extractor = extractor;
        this.collector = collector;
        this.player = player;
    }

    /**
     * @return the extractor
     */
    public @NotNull Structure getExtractor() {
        return extractor;
    }

    /**
     * @return the collector
     */
    public @NotNull Structure getCollector() {
        return collector;
    }

    /**
     * @return the player making the action
     */
    public @NotNull Player getPlayer() {
        return player;
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
