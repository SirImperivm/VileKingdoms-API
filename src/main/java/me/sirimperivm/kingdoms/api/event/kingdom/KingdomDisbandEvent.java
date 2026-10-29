package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a kingdom has been disbanded or destroyed: it does not exist anymore.
 */
public class KingdomDisbandEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID kingdomId;
    private final String name;
    private final boolean admin;

    /**
     * Creates the event.
     *
     * @param kingdomId the id the kingdom had
     * @param name the name the kingdom had
     * @param admin whether an admin destroyed it
     */
    public KingdomDisbandEvent(@NotNull UUID kingdomId, @NotNull String name, boolean admin) {
        this.kingdomId = kingdomId;
        this.name = name;
        this.admin = admin;
    }

    /**
     * @return the id the kingdom had
     */
    public @NotNull UUID getKingdomId() {
        return kingdomId;
    }

    /**
     * @return the name the kingdom had
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * @return whether an admin destroyed it
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
