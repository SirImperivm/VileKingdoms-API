package me.sirimperivm.kingdoms.api.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * War declared by a town (the attacker) to a town of an enemy kingdom (the defender).
 */
public interface War {

    /** @return the id of the war, as shown by /town admin war list */
    int getId();

    @NotNull UUID getAttackerId();

    @NotNull UUID getDefenderId();

    /** @return the attacking town, null when it does not exist anymore */
    @Nullable Town getAttacker();

    /** @return the defending town, null when it does not exist anymore */
    @Nullable Town getDefender();

    /** @return the player who declared the war, null when unknown */
    @Nullable UUID getDeclarerId();

    double getStake();

    /** @return the members of the attack squad */
    @NotNull Set<UUID> getSquad();

    long getStartedAt();

    /** @return when the war ends, or ended, in epoch milliseconds */
    long getEndedAt();

    @NotNull WarOutcome getOutcome();

    /** @return the chunks of the defender contested by the attackers */
    int getContestedChunks();
}
