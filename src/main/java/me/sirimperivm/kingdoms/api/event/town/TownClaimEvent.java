package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.ChunkPosition;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a chunk has been claimed for a town.
 */
public class TownClaimEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final ChunkPosition chunk;
    private final Player player;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param chunk the chunk
     * @param player who claimed it
     */
    public TownClaimEvent(@NotNull Town town, @NotNull ChunkPosition chunk, @Nullable Player player) {
        this.town = town;
        this.chunk = chunk;
        this.player = player;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the chunk
     */
    public @NotNull ChunkPosition getChunk() {
        return chunk;
    }

    /**
     * @return who claimed it
     */
    public @Nullable Player getPlayer() {
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
