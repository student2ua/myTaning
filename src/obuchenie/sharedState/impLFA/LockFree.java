package obuchenie.sharedState.impLFA;

import obuchenie.sharedState.FibonacciGenerator;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * User: tor
 * Date: 21.03.14
 * Time: 11:54
 * алгоритмы построены на атомарных операциях, предоставляемых процессорами. Примером служит метод get в
 * ConcurrentHashMap. Чтобы писать неблокирующие алгоритмы, имеет смысл воспользоваться существующими неблокирующими
 * классами: ConcurrentLinkedQueue, ConcurrentHashMap
 */
public class LockFree implements FibonacciGenerator<BigInteger> {
    private class State {
        final BigInteger curr;
        final BigInteger next;

        public State(BigInteger curr, BigInteger next) {
            this.curr = curr;
            this.next = next;
        }
    }

    // Сделаем состояние класса атомарным
    private AtomicReference<State> atomicReference = new AtomicReference<State>(new State(BigInteger.ONE, BigInteger.ONE));

    @Override
    public BigInteger next() {
        BigInteger value = null;
        while (true) {
            // сохраняем состояние класса
            State state = atomicReference.get();
            // то что возвращаем если удалось изменить состояние класса
            value = state.curr;
            // конструируем новое состояние
            State newState = new State(state.next, state.curr.add(state.next));
            // если за время пока мы конструировали новое состояние
            // оно осталось прежним, то заменяем состояние на новое,
            // иначе пробуем сконструировать еще раз
            if (atomicReference.compareAndSet(state, newState)) {
                break;
            }
        }
        return value;
    }

    @Override
    public BigInteger val() {
        return atomicReference.get().curr;
    }
}
