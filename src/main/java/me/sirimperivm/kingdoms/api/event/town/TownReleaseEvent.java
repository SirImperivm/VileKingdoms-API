package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a town has been released: it does not exist anymore.
 */
public class TownReleaseEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID townId;
    private final String name;
    private final UUID kingdomId;

    /**
     * Creates the event.
     *
     * @param townId the id the town had
     * @param name the name the town had
     * @param kingdomId the kingdom the town belonged to
     */
    public TownReleaseEvent(@NotNull UUID townId, @NotNull String name, @NotNull UUID kingdomId) {
        this.townId = townId;
        this.name = name;
        this.kingdomId = kingdomId;
    }

    /**
     * @return the id the town had
     */
    public @NotNull UUID getTownId() {
        return townId;
    }

    /**
     * @return the name the town had
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * @return the kingdom the town belonged to
     */
    public @NotNull UUID getKingdomId() {
        return kingdomId;
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
