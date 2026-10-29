package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a player has joined a town.
 */
public class TownMemberJoinEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final UUID member;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param member the new member
     */
    public TownMemberJoinEvent(@NotNull Town town, @NotNull UUID member) {
        this.town = town;
        this.member = member;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the new member
     */
    public @NotNull UUID getMember() {
        return member;
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
