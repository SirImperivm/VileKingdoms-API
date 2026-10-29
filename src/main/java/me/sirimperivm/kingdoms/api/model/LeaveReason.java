package me.sirimperivm.kingdoms.api.model;

/**
 * Why a player is not a member of a town anymore.
 */
public enum LeaveReason {
    LEFT,
    KICKED,
    BANNED,
    REMOVED_BY_ADMIN,
    TAX_NOT_PAID,
    ENEMY_CONFLICT,
    TOWN_RELEASED
}
