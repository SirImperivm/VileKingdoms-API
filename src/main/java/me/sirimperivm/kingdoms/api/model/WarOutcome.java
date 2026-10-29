package me.sirimperivm.kingdoms.api.model;

/**
 * Outcome of a war: {@link #NONE} while it is in progress.
 */
public enum WarOutcome {
    NONE,
    ATTACKER_WON,
    DEFENDER_WON,
    ABANDONED,
    DRAW
}
