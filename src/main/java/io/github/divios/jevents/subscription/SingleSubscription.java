package io.github.divios.jevents.subscription;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SingleSubscription<T extends Event> implements Listener, Subscription {

    private final Class<T> clazz;
    private final BiConsumer<Subscription, T> action;
    private final EventPriority priority;
    private final Set<Predicate<T>> filters = new HashSet<>();

    protected SingleSubscription(
            Plugin plugin,
            Class<T> clazz,
            BiConsumer<Subscription, T> action,
            EventPriority priority,
            Set<Predicate<T>> filters) {

        this.clazz = clazz;
        this.action = action;
        this.priority = priority;
        this.filters.addAll(filters);

        initialize(plugin);
    }

    private void initialize(Plugin plugin) {
        Bukkit.getPluginManager().registerEvent(clazz, this, priority, (listener, event) -> handleEvent((T) event), plugin);
    }

    private void handleEvent(T e) {
        if (e.getClass().equals(clazz) && passFilters(e)) {
            action.accept(this, e);
        }
    }

    private boolean passFilters(T e) {
        for (Predicate<T> filter : filters) {
            if (!filter.test(e)) return false;
        }
        return true;
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}