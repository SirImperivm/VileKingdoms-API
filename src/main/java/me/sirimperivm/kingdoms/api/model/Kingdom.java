package me.sirimperivm.kingdoms.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * A kingdom: a group of towns ruled by a king. The view always shows the current state of the kingdom.
 */
public interface Kingdom {

    @NotNull UUID getId();

    /** @return the name used in the commands, unique among the kingdoms */
    @NotNull String getName();

    /** @return the name shown to the players, with its colors */
    @NotNull String getDisplayName();

    @Nullable String getDescription();

    /** @return the king of the kingdom, null when it has none */
    @Nullable UUID getKingId();

    /** @return the members of the kingdom: the members of its towns, and its king */
    @NotNull Set<UUID> getMembers();

    @NotNull Collection<Town> getTowns();

    /** @return the capital, destination of /town capital; null when the king has not chosen it */
    @Nullable Town getCapital();

    /** @return the relation with the other kingdom: {@link RelationType#NEUTRAL} when none was set */
    @NotNull RelationType getRelation(@NotNull Kingdom other);

    /** @return whether the members of the kingdom can hit each other in its towns */
    boolean isFriendlyFire();

    /** @return the emeralds of the kingdom: the sum of the ones of its towns */
    double getEmeralds();

    /** @return the worth of the kingdom: the sum of the worth of its towns */
    double getWorth();

    /** @return the money of the kingdom: the sum of the banks of its towns */
    double getBank();

    /** @return the claims of the kingdom: the sum of the ones of its towns */
    int getClaimsCount();
}
