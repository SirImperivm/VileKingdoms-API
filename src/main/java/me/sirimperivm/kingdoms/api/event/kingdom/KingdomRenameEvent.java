package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a kingdom has been renamed.
 */
public class KingdomRenameEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final String oldDisplayName;

    /**
     * Creates the event.
     *
     * @param kingdom the kingdom, with its new name
     * @param oldDisplayName the display name it had
     */
    public KingdomRenameEvent(@NotNull Kingdom kingdom, @NotNull String oldDisplayName) {
        this.kingdom = kingdom;
        this.oldDisplayName = oldDisplayName;
    }

    /**
     * @return the kingdom, with its new name
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return the display name it had
     */
    public @NotNull String getOldDisplayName() {
        return oldDisplayName;
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
