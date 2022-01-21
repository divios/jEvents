package io.github.divios.jevents.subscription;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class SubscriptionBuilder<T extends Event> {

    private final Plugin plugin;
    private final Class<T> clazz;
    private final Set<Predicate<T>> filters = new HashSet<>();
    private EventPriority priority;

    public SubscriptionBuilder(Plugin plugin, Class<T> clazz) {
        this(plugin, clazz, EventPriority.NORMAL);
    }

    private SubscriptionBuilder(Plugin plugin, Class<T> clazz, EventPriority priority) {
        this.plugin = plugin;
        this.clazz = clazz;
        this.priority = priority;
    }

    public SubscriptionBuilder<T> filter(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    public SubscriptionBuilder<T> setPriority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    public Subscription handler(Consumer<T> consumer) {
        return biHandler((tSubscription, t) -> consumer.accept(t));
    }

    public Subscription biHandler(BiConsumer<Subscription, T> consumer) {
        return new SingleSubscription<>(plugin, clazz, consumer, priority, filters);
    }

}