package io.github.divios.jevents;

import com.google.common.base.Preconditions;
import io.github.divios.jevents.subscription.SubscriptionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class JEvents {

    private static JavaPlugin providerPlugin;

    public static void register(JavaPlugin providerPlugin) {
        JEvents.providerPlugin = providerPlugin;
    }

    public static <T extends Event> SubscriptionBuilder<T> subscribe(Class<T> clazz) {
        return subscribe(clazz, EventPriority.NORMAL);
    }

    public static <T extends Event> SubscriptionBuilder<T> subscribe(Class<T> clazz, EventPriority priority) {
        Preconditions.checkNotNull(providerPlugin, "No plugin registered");
        return new SubscriptionBuilder<>(providerPlugin, clazz);
    }

    public static <T extends Event> void callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
    }

}
