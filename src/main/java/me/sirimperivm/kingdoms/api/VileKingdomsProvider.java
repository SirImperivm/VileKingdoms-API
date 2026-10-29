package me.sirimperivm.kingdoms.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holder of the API of the enabled plugin. Use {@link VileKingdomsAPI#get()}.
 */
public final class VileKingdomsProvider {

    private static @Nullable VileKingdomsAPI instance;

    private VileKingdomsProvider() {
    }

    static @NotNull VileKingdomsAPI get() {
        VileKingdomsAPI api = instance;
        if (api == null) throw new IllegalStateException("VileKingdoms is not enabled.");
        return api;
    }

    /**
     * Sets the API of the plugin. Called only by VileKingdoms.
     */
    @ApiStatus.Internal
    public static void register(@Nullable VileKingdomsAPI api) {
        instance = api;
    }
}
