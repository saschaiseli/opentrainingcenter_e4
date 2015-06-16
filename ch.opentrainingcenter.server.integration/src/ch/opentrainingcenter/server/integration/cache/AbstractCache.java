package ch.opentrainingcenter.server.integration.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import ch.opentrainingcenter.common.assertions.Assertions;

public abstract class AbstractCache<K, V> implements ICache<K, V> {

    private static final Logger LOG = Logger.getLogger(AbstractCache.class);
    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public abstract K getKey(V value);

    public abstract String getName();

    @Override
    public String toString() {
        return String.format("%s-Cache: Anzahl Elemente: %s", getName(), size()); //$NON-NLS-1$
    }

    @Override
    public void addAll(final List<V> values) {
        Assertions.notNull(values);
        final List<V> added = new ArrayList<>();
        final List<V> changed = new ArrayList<>();
        for (final V value : values) {
            final V previous = cache.put(getKey(value), value);
            if (previous == null) {
                added.add(value);
            } else {
                changed.add(value);
            }
        }
        if (!added.isEmpty()) {
            LOG.info(String.format("Element added in Cache '%s' Size: %s", getName(), cache.size())); //$NON-NLS-1$
        }
        if (!changed.isEmpty()) {
            LOG.info(String.format("Element updated in Cache '%s' Size: %s", getName(), cache.size())); //$NON-NLS-1$
        }
    }

    @Override
    public V get(final K key) {
        return cache.get(key);
    }

    @Override
    public void remove(final K key) {
        final V value = cache.remove(key);
        final List<V> values = new ArrayList<>();
        if (value != null) {
            LOG.info(String.format("Element removed --> Cache (%s) Size: %s", value.getClass().getName(), cache.size())); //$NON-NLS-1$
            values.add(value);
        }
    }

    @Override
    public void remove(final List<K> keys) {
        for (final K key : keys) {
            remove(key);
        }
    }

    @Override
    public boolean contains(final K key) {
        return cache.containsKey(key);
    }

    public int size() {
        return cache.size();
    }

    public List<V> getAll() {
        return new ArrayList<>(cache.values());
    }

    public List<V> getSortedElements(final Comparator<V> comparator) {
        final List<V> values = new ArrayList<>(cache.values());
        Collections.sort(values, comparator);
        return values;
    }

    public void resetCache() {
        cache.clear();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
