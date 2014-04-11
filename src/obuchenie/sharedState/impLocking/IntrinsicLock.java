package obuchenie.sharedState.impLocking;

import obuchenie.sharedState.FibonacciGenerator;

import java.math.BigInteger;

/**
 * User: tor
 * Date: 20.03.14
 * Time: 17:41
 * Первый способ сделать класс корректно работающим в многопоточной среде — это использовать блокировки
 * и объявить все методы synchronized. Примером может служить класс Vector.
 */
public class IntrinsicLock implements FibonacciGenerator<BigInteger> {
    BigInteger curr = BigInteger.ONE;
    BigInteger next = BigInteger.ONE;

    @Override
    public synchronized BigInteger next() {
        BigInteger rez = curr;
        curr = next;
        next = rez.add(next);
        return rez;
    }

    @Override
    public synchronized BigInteger val() {
        return curr;
    }
}
