package me.sirimperivm.kingdoms.api.model;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * A structure placed in a claim of a town.
 */
public interface Structure {

    @NotNull UUID getId();

    @NotNull StructureType getType();

    @NotNull String getStyle();

    int getLevel();

    /** @return the level of the defenses bought for the structure: 0 when it has none */
    int getDefenseLevel();

    /** @return the town of the structure, null only while it is being removed */
    @Nullable Town getTown();

    /** @return the lowest corner of the structure at the ground, null when its world is not loaded */
    @Nullable Location getLocation();

    /** @return the emeralds of the town for the town hall, 0 for the other structures */
    double getEmeralds();

    /** @return the collector an extractor is linked to, null when it is not linked and for the other structures */
    @Nullable Structure getLinkedCollector();
}
