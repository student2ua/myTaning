package obuchenie.sharedState.impFG;

import obuchenie.sharedState.FibonacciGenerator;

import java.math.BigInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: tor
 * Date: 20.03.14
 * Time: 17:48
 * оградить локами только те секции, в которых происходит работа с разделяемым состоянием
 */
public class FineGrainedLock implements FibonacciGenerator<BigInteger> {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private BigInteger curr = BigInteger.ONE;
    private BigInteger next = BigInteger.ONE;


    @Override
    public BigInteger next() {
        BigInteger rez;
        lock.writeLock().lock();
        try {
            // Вход другим потокам запрещен
            rez = curr;
            curr = next;
            next = rez.add(next);
            return rez;
        } finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    public BigInteger val() {
        lock.readLock().lock();
        try {
            // При отпущенном write lock
            // Допуст`им вход множества потоков
            return curr;
        } finally {
            lock.readLock().unlock();
        }
    }
}
