package me.sirimperivm.kingdoms.api.model;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Position of a chunk, the unit of the claims of the towns.
 *
 * @param world the name of the world
 * @param x     the x of the chunk (the block x divided by 16)
 * @param z     the z of the chunk (the block z divided by 16)
 */
public record ChunkPosition(@NotNull String world, int x, int z) {

    public static @NotNull ChunkPosition of(@NotNull Chunk chunk) {
        return new ChunkPosition(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public static @NotNull ChunkPosition of(@NotNull Location location) {
        return new ChunkPosition(location.getWorld().getName(), location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }
}
