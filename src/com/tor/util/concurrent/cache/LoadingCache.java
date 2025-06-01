package com.tor.util.concurrent.cache;

import java.util.concurrent.*;

/**
 * User: tor
 * Time: 22:13
 * org.springframework.cglib.core.internal
 * хранит вычисленные значения, ассоциированные с ключами;
 *
 * вычисляет значение при первом доступе через loader;
 *
 * поддерживает безопасный доступ из нескольких потоков;
 *
 * позволяет сопоставить пользовательский ключ K с кэшируемым ключом KK через keyMapper
 */
public class LoadingCache<K, KK, V> {
    protected final ConcurrentMap<KK, Object> map;
    protected final Function<K, V> loader;
    protected final Function<K, KK> keyMapper;
    public static final Function IDENTITY = new Function() {
        @Override
        public Object apply(Object key) {
            return key;
        }
    };
    /**
     K — входной ключ (например, сложный объект).
     KK — фактический ключ для хранения в кэше (обычно String, Integer и т. п.).
     V — значение, возвращаемое и кэшируемое.
     */
    public LoadingCache(Function<K, KK> keyMapper, Function<K, V> loader) {
        this.keyMapper = keyMapper;
        this.loader = loader;
        this.map = new ConcurrentHashMap<KK, Object>();
    }

    public static <K> Function<K, K> identity() {
        return IDENTITY;
    }

    public V get(K key) {
        KK cacheKey = (KK) this.keyMapper.apply(key);
        Object v = this.map.get(cacheKey);
        return (V) (v != null && !(v instanceof FutureTask) ? v : this.createEntry(key, cacheKey, v));
    }

    protected V createEntry(final K key, KK cacheKey, Object v) {
        boolean creator = false;
        FutureTask<V> task;
        if (v != null) {
            task = (FutureTask<V>) v;
        } else {
            task = new FutureTask<V>(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return LoadingCache.this.loader.apply(key);
                }
            });

            Object prevTask = this.map.putIfAbsent(cacheKey, task);
            if (prevTask == null) {
                creator = true;
                task.run();
            } else {
                if (!(prevTask instanceof FutureTask)) {
                    return (V) prevTask;
                }

                task = (FutureTask<V>) prevTask;
            }
        }

        V result;
        try

        {
            result = (V) task.get();
        } catch (
                InterruptedException e
                )

        {
            throw new IllegalStateException("Interrupted while loading cache item", e);
        } catch (
                ExecutionException e
                )

        {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }

            throw new IllegalStateException("Unable to load cache item", cause);
        }

        if (creator)

        {
            this.map.put(cacheKey, result);
        }

        return result;
    }
}
