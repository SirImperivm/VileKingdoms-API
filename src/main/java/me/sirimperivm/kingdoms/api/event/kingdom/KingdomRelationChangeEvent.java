package me.sirimperivm.kingdoms.api.event.kingdom;

import me.sirimperivm.kingdoms.api.event.VileKingdomsEvent;
import me.sirimperivm.kingdoms.api.model.Kingdom;
import me.sirimperivm.kingdoms.api.model.RelationType;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the relation between two kingdoms has changed.
 */
public class KingdomRelationChangeEvent extends VileKingdomsEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Kingdom kingdom;
    private final Kingdom other;
    private final RelationType oldRelation;
    private final RelationType newRelation;

    /**
     * Creates the event.
     *
     * @param kingdom the kingdom
     * @param other the other kingdom
     * @param oldRelation the relation they had
     * @param newRelation their new relation
     */
    public KingdomRelationChangeEvent(@NotNull Kingdom kingdom, @NotNull Kingdom other, @NotNull RelationType oldRelation, @NotNull RelationType newRelation) {
        this.kingdom = kingdom;
        this.other = other;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    /**
     * @return the kingdom
     */
    public @NotNull Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * @return the other kingdom
     */
    public @NotNull Kingdom getOther() {
        return other;
    }

    /**
     * @return the relation they had
     */
    public @NotNull RelationType getOldRelation() {
        return oldRelation;
    }

    /**
     * @return their new relation
     */
    public @NotNull RelationType getNewRelation() {
        return newRelation;
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
