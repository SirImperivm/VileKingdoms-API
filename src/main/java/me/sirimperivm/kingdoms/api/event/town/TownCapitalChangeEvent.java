package me.sirimperivm.kingdoms.api.event.town;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import me.sirimperivm.kingdoms.api.model.Town;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when the king has chosen a new capital.
 */
public class TownCapitalChangeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final Town oldCapital;
    private final Town newCapital;

    /**
     * Creates the event.
     *
     * @param kingdom the kingdom
     * @param oldCapital the capital it had
     * @param newCapital the new capital
     */
    public TownCapitalChangeEvent(@NotNull Kingdom kingdom, @Nullable Town oldCapital, @NotNull Town newCapital) {
        this.kingdom = kingdom;
        this.oldCapital = oldCapital;
        this.newCapital = newCapital;
    }

    /**
     * @return the kingdom
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return the capital it had
     */
    public @Nullable Town getOldCapital() {
        return oldCapital;
    }

    /**
     * @return the new capital
     */
    public @NotNull Town getNewCapital() {
        return newCapital;
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
