package com.tor.util.concurrent.cache;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: tor
 * Date: 01.06.25
 * Time: 22:56
 *
 * Потокобезопасный ленивый кэш с поддержкой TTL (времени жизни) и автоматической очисткой устаревших записей.
 *
 * <p>Кэш загружает значения по мере необходимости через заданную {@code loader}-функцию.
 * Каждый ключ сначала преобразуется в ключ кэширования через {@code keyMapper}.
 *
 * <p>Ключевые особенности:
 * <ul>
 *   <li>Параметризуемые ключи и значения (K, KK, V)</li>
 *   <li>TTL — записи устаревают после заданного времени</li>
 *   <li>Очистка устаревших записей через фоновый поток</li>
 *   <li>Защита от гонок с использованием {@link FutureTask}</li>
 * </ul>
 *
 * <p>Пример использования:
 * <pre>
 * LoadingCache&lt;String, String, String&gt; cache = new LoadingCache&lt;&gt;(
 *     LoadingCache.identity(),
 *     new Function&lt;String, String&gt;() {
 *         public String apply(String key) {
 *             return "value for " + key;
 *         }
 *     },
 *     5000, // TTL: 5 секунд
 *     2000  // Очистка: каждые 2 секунды
 * );
 * String result = cache.get("abc");
 * </pre>
 *
 * @param <K> исходный ключ
 * @param <KK> ключ, используемый в кэше
 * @param <V> тип значения
 */


public class LoadingCacheTTL<K, KK, V> {
    protected final ConcurrentMap<KK, Object> map;
    protected final Function<K, V> loader;
    protected final Function<K, KK> keyMapper;
    protected final long ttlMillis;
    private final ScheduledExecutorService cleaner;

    private static class EntryWrapper<V> {
        final V value;
        final long timestamp;

        EntryWrapper(V value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    @SuppressWarnings("rawtypes")
    public static final Function IDENTITY = new Function() {
        @Override
        public Object apply(Object key) {
            return key;
        }
    };

    public LoadingCacheTTL(final Function<K, KK> keyMapper, final Function<K, V> loader,
                           long ttlMillis, long cleanupIntervalMillis) {
        this.keyMapper = keyMapper;
        this.loader = loader;
        this.map = new ConcurrentHashMap<KK, Object>();
        this.ttlMillis = ttlMillis;

        this.cleaner = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "LoadingCache-Cleanup-" + count.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        });

        this.cleaner.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                cleanUp();
            }
        }, cleanupIntervalMillis, cleanupIntervalMillis, TimeUnit.MILLISECONDS);
    }

    @SuppressWarnings("unchecked")
    public static <K> Function<K, K> identity() {
        return IDENTITY;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        KK cacheKey = keyMapper.apply(key);
        Object raw = map.get(cacheKey);

        if (raw instanceof EntryWrapper) {
            EntryWrapper<V> entry = (EntryWrapper<V>) raw;
            if (System.currentTimeMillis() - entry.timestamp <= ttlMillis) {
                return entry.value;
            }
        }

        return createEntry(key, cacheKey, raw);
    }

    @SuppressWarnings("unchecked")
    protected V createEntry(final K key, KK cacheKey, Object v) {
        boolean creator = false;
        FutureTask<V> task;

        if (v instanceof FutureTask) {
            task = (FutureTask<V>) v;
        } else {
            task = new FutureTask<V>(new Callable<V>() {
                public V call() {
                    return loader.apply(key);
                }
            });

            Object prev = map.putIfAbsent(cacheKey, task);
            if (prev == null) {
                creator = true;
                task.run();
            } else if (prev instanceof FutureTask) {
                task = (FutureTask<V>) prev;
            } else if (prev instanceof EntryWrapper) {
                EntryWrapper<V> entry = (EntryWrapper<V>) prev;
                if (System.currentTimeMillis() - entry.timestamp <= ttlMillis) {
                    return entry.value;
                } else {
                    map.remove(cacheKey, prev); // remove stale
                    return createEntry(key, cacheKey, null); // retry
                }
            } else {
                return (V) prev;
            }
        }

        try {
            V result = task.get();
            if (creator) {
                map.put(cacheKey, new EntryWrapper<V>(result, System.currentTimeMillis()));
            }
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while loading cache item", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new IllegalStateException("Unable to load cache item", cause);
        }
    }

    public void cleanUp() {
        long now = System.currentTimeMillis();
        for (Map.Entry<KK, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof EntryWrapper) {
                EntryWrapper<?> wrapper = (EntryWrapper<?>) value;
                if (now - wrapper.timestamp > ttlMillis) {
                    map.remove(entry.getKey(), value);
                }
            }
        }
    }

    public void shutdown() {
        cleaner.shutdown();
    }
}
