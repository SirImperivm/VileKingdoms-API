package me.sirimperivm.kingdoms.api.event.war;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.ChunkPosition;
import me.sirimperivm.kingdoms.api.model.War;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the attackers have contested a chunk of the defender.
 */
public class WarChunkContestEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final War war;
    private final ChunkPosition chunk;

    /**
     * Creates the event.
     *
     * @param war the war
     * @param chunk the contested chunk
     */
    public WarChunkContestEvent(@NotNull War war, @NotNull ChunkPosition chunk) {
        this.war = war;
        this.chunk = chunk;
    }

    /**
     * @return the war
     */
    public @NotNull War getWar() {
        return war;
    }

    /**
     * @return the contested chunk
     */
    public @NotNull ChunkPosition getChunk() {
        return chunk;
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
