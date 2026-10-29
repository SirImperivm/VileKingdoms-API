package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.LeaveReason;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a player is not a member of a town anymore.
 */
public class TownMemberLeaveEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Town town;
    private final UUID member;
    private final LeaveReason reason;

    /**
     * Creates the event.
     *
     * @param town the town
     * @param member the former member
     * @param reason why
     */
    public TownMemberLeaveEvent(@NotNull Town town, @NotNull UUID member, @NotNull LeaveReason reason) {
        this.town = town;
        this.member = member;
        this.reason = reason;
    }

    /**
     * @return the town
     */
    public @NotNull Town getTown() {
        return town;
    }

    /**
     * @return the former member
     */
    public @NotNull UUID getMember() {
        return member;
    }

    /**
     * @return why
     */
    public @NotNull LeaveReason getReason() {
        return reason;
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
