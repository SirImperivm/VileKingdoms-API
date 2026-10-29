package me.sirimperivm.kingdoms.api;

import me.sirimperivm.kingdoms.api.model.Kingdom;
import me.sirimperivm.kingdoms.api.model.Town;
import me.sirimperivm.kingdoms.api.model.War;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Entry point of the API of VileKingdoms, available while the plugin is enabled:
 * {@code VileKingdomsAPI api = VileKingdomsAPI.get();}, or through the services of Bukkit.
 * <p>
 * Every method must be called from the main thread of the server. The views returned show the current state of
 * kingdoms, towns and wars: they are read only, the changes are made by the players and the admins.
 */
public interface VileKingdomsAPI {

    /**
     * @return the API of the enabled plugin
     * @throws IllegalStateException when VileKingdoms is not enabled
     */
    static @NotNull VileKingdomsAPI get() {
        return VileKingdomsProvider.get();
    }

    /** @return the version of the plugin providing the API */
    @NotNull String getVersion();

    /** @return whether the kingdoms and the towns have been loaded from the database: before, the lookups are empty */
    boolean isLoaded();

    @NotNull Collection<Kingdom> getKingdoms();

    @NotNull Optional<Kingdom> getKingdom(@NotNull UUID id);

    /** @param name the name or the display name of the kingdom */
    @NotNull Optional<Kingdom> getKingdom(@NotNull String name);

    /** @return the primary kingdom of the player: the one of the town they manage, otherwise the one they rule, otherwise the first by name */
    @NotNull Optional<Kingdom> getPrimaryKingdom(@NotNull UUID player);

    @NotNull Collection<Town> getTowns();

    @NotNull Optional<Town> getTown(@NotNull UUID id);

    /** @param name the name or the display name of the town */
    @NotNull Optional<Town> getTown(@NotNull String name);

    /** @return the town owning the chunk of the location */
    @NotNull Optional<Town> getTownAt(@NotNull Location location);

    /** @return the towns the player is a member of */
    @NotNull Collection<Town> getTownsOf(@NotNull UUID player);

    /** @return the war in progress of the town, as attacker or defender */
    @NotNull Optional<War> getWarOf(@NotNull Town town);

    @NotNull Collection<War> getActiveWars();

    /** @return whether the current period is a period of war, when the wars can be declared */
    boolean isWarPeriod();

    /** @return when the current period ends, in epoch milliseconds; {@link Long#MAX_VALUE} for a permanent peace */
    long getPeriodEndsAt();
}
