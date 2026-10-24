/**
 * Этот пакет содержит утилиты и структуры данных для конкурентного программирования,
 * включая lock-free реализации, основанные на атомарных операциях.
 * <p>Основные компоненты:
 * <ul>
 *   <li>{@link com.tor.util.concurrent.TtlHashMap} — реализация Map с поддержкой TTL (времени жизни записей).</li>
 *   <li>{@link com.tor.util.concurrent.WeakConcurrentHashMap} — Map с использованием слабых ссылок, безопасный для многопоточности.</li>
 *   <li>{@link com.tor.util.concurrent.SelfExpiringHashMap} — Map, автоматически удаляющий записи по таймеру.</li>
 *   <li>{@link com.tor.util.concurrent.BlockingExecutor} — ограниченный Executor, блокирующий поток при превышении очереди.</li>
 *   <li>{@link com.tor.util.concurrent.cache.LoadingCache} —  ленивый, потокобезопасный кэш с отложенной загрузкой и функцией преобразования ключей.</li>
 * </ul>
 * <p>См. также:
 * <ul>
 *   <li><a href="http://habrahabr.ru/company/luxoft/blog/157273/">Luxoft: многопоточность и Java</a></li>
 *   <li><a href="https://www.baeldung.com/lock-free-programming">Baeldung: Lock-Free Programming</a></li>
 * </ul>
 * */
package com.tor.util.concurrent;