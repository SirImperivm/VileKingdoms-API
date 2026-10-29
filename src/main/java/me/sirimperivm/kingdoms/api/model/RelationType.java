package me.sirimperivm.kingdoms.api.model;

/**
 * Relation between two kingdoms. Two players without a kingdom, or with one only, are neutral.
 */
public enum RelationType {
    ALLY,
    NEUTRAL,
    ENEMY
}
