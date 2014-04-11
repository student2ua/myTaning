package obuchenie.sharedState;

/**
 * User: tor
 * Date: 20.03.14
 * Time: 17:40
 * http://habrahabr.ru/post/216049/
 */
public interface FibonacciGenerator<T> {
    /**
     * Следующее сгенерированное значение
     */
    T next();

    /**
     * Текущее значение в генераторе
     */
    public T val();

}
