package me.sirimperivm.kingdoms.api.model;

import me.sirimperivm.kingdoms.api.VileKingdomsAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Position of a land, the unit of the claims of the towns: a square of {@link VileKingdomsAPI#getLandSize()} blocks,
 * aligned to the origin of the world (the land 0,0 starts at the block 0,0).
 *
 * @param world the name of the world
 * @param x     the x of the land: the block x divided by the side of the lands, rounded down
 * @param z     the z of the land: the block z divided by the side of the lands, rounded down
 */
public record LandPosition(@NotNull String world, int x, int z) {

    /**
     * @return the land holding the location; VileKingdoms must be enabled
     */
    public static @NotNull LandPosition of(@NotNull Location location) {
        int side = VileKingdomsAPI.get().getLandSize();
        return new LandPosition(location.getWorld().getName(), Math.floorDiv(location.getBlockX(), side), Math.floorDiv(location.getBlockZ(), side));
    }

    /**
     * @return the x of the first block of the land; VileKingdoms must be enabled
     */
    public int getMinBlockX() {
        return x * VileKingdomsAPI.get().getLandSize();
    }

    /**
     * @return the z of the first block of the land; VileKingdoms must be enabled
     */
    public int getMinBlockZ() {
        return z * VileKingdomsAPI.get().getLandSize();
    }
}
