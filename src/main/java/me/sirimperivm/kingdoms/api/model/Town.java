package me.sirimperivm.kingdoms.api.model;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * A town of a kingdom: its territory is made of claims, each one a land. The view always shows the current state of
 * the town.
 */
public interface Town {

    @NotNull UUID getId();

    /** @return the name used in the commands, unique among the towns */
    @NotNull String getName();

    /** @return the name shown to the players */
    @NotNull String getDisplayName();

    /** @return the kingdom of the town, null only while the town is being deleted */
    @Nullable Kingdom getKingdom();

    /** @return the rank of the town, the level of its town hall: from 1 */
    int getRank();

    /** @return the style of the structures of the town */
    @NotNull String getStyle();

    @NotNull Set<UUID> getMembers();

    boolean isMember(@NotNull UUID player);

    /** @return the name of the town role of the member, null when the player is not a member */
    @Nullable String getRoleName(@NotNull UUID player);

    @NotNull Collection<LandPosition> getClaims();

    boolean hasClaim(@NotNull LandPosition land);

    /** @return the money of the town (Vault economy) */
    double getBank();

    /** @return the emeralds kept by the collectors of the town */
    double getEmeralds();

    /** @return the value of the items in the containers of the claims, updated at regular intervals */
    double getWorth();

    /** @return until when the town cannot be attacked, in epoch milliseconds; 0 when it is not immune */
    long getImmuneUntil();

    /** @return whether the town has been destroyed by a war and cannot be entered now */
    boolean isDestroyed();

    @NotNull Collection<Structure> getStructures();

    /** @return the spawn point of the town hall, null when its world is not loaded */
    @Nullable Location getSpawn();
}
