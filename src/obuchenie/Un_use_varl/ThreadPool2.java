package obuchenie.Un_use_varl;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 17.09.2009
 * Time: 12:46:11
 * To change this template use File | Settings | File Templates.
 */

/**
 * Пул потоков
 */
public class ThreadPool2 {
    /**
     * Получить экземпляр пула потоков
     */
    public static ThreadPool2 getInstance() {
        return theInstance;
    }

    /**
     * Поток для повторного использования
     */
    static class PooledThread extends Thread {
        PooledThread next;
        Object ready; // объект, используемый для ожидания нового задания
        Object done;  // объект, используемый для сигнализации завершения работы
        boolean busy; // флажок отмечающий, что поток занят
        boolean doneNotificationNeeded; // ждёт ли кто-нибудь завершения работы
        Runnable task; // что нужно сделать
        ThreadPool2 pool2; // пул потоков


        public void run() {
            try {
                synchronized (ready) { // блокируем ready – готовимся к ожиданию работы
                    while (!pool2.closed) { // проверяем не закрыт ли пул
                        synchronized (done) { // блокировка ready
                            busy = false;    // мы закончили работу начатую на предыдущей итерации цикла
                            if (doneNotificationNeeded) { // если кто-то ждёт завершения работы…
                                done.notify();     // то пошлём уведомление, что мы её закончили
                            }
                        }
                        ready.wait();              // ждём нового задания
                        if (task != null) {        // если мы его получили…
                            task.run();            // то выполняем
                        } else {
                            break;                 // иначе завершаем работу
                        }
                    }
                }
            } catch (InterruptedException x) {
            }
        }

        final void wakeUp(Runnable t) { // запустить задание на выполнение
            synchronized (ready) { // блокировать readу, чтобы можно было послать уведомление
                busy = true; // установить флаг занятости
                doneNotificationNeeded = false;
                task = t;
                ready.notify(); // послать уведомление свободному потоку
            }
        }

        final void waitCompletion() throws InterruptedException {
            synchronized (done) { // блокировать done для ожидания
                if (busy) { // если задание ещё не завершилось…
                    doneNotificationNeeded = true; // то поставить флаг ожидания завершения
                    done.wait(); // и подождать
                }
            }
        }

        PooledThread(ThreadPool2 pool2) {
            this.pool2 = pool2;
            ready = new Object();
            done = new Object();
            busy = true;
            setDaemon(true); // запускаем поток как демона, чтобы не ждать его завершения
            // при выходе из программы
            this.start();
        }
    }

    /**
     * Найти свободный поток и запустить в нём задание на выполнение
     *
     * @param task объект реализующий интерфейс Runnable,
     *             метод run которого будет выполнен потоком
     * @return поток, выделенный для данного задания
     *         (его можно использовать только в методе ThreadPool2.join)
     */
    public Thread start(Runnable task) {
        PooledThread thread;
        synchronized (this) { // для работы со списком нужна блокировка
            while (availableThreadList == null) { // если список свободных потоков пуст
                try {
                    if (nActiveThreads == maxThreads) { // и если достигнуто ограничение на
                        // максимальное число потоков
                        deficit += 1; // то отметить, что есть задачи, ждущие выполнения
                        wait();  // и подождать пока какой-нибудь из потоков не освободится
                    } else {
                        availableThreadList = new PooledThread(this); // создать новый поток
                        availableThreadList.waitCompletion(); // и дождаться момента,
                        // когда он стартует и будет
                        // готов к получению задания
                    }
                } catch (InterruptedException x) {
                    return null;
                }
            }
            thread = availableThreadList; // взять поток из списка свободных

            availableThreadList = thread.next;
            nActiveThreads += 1; // увеличит счётчик активных потоков
        }
        thread.wakeUp(task); // запустить задание на выполнение
        return thread;
    }

    /**
     * Дождаться завершения задания. Поток при этом возвращается в список свободных
     *
     * @param thread поток, возвращённый методом ThreadPool2.start
     */
    public void join(Thread thread) throws InterruptedException {
        PooledThread t = (PooledThread) thread;
        t.waitCompletion(); // дождаться завершения задания
        synchronized (this) { // для работы со списком нужна блокировка
            t.next = availableThreadList; // добавить поток в список свободных
            availableThreadList = t;
            nActiveThreads -= 1; // уменьшить значение счётчика активных потоков
            if (deficit > 0) { // если есть потоки ждущие уведомления…
                notify();  // то послать уведомление о том, что поток освободился
                deficit -= 1;
            }
        }
    }

    /**
     * Закрытие пула потоков. Подождать окончания работы всех активных потоков и
     * завершить  все свободные потоки
     */
    public synchronized void close() {
        closed = true; // ставим флаг прекращения работы
        while (nActiveThreads > 0) { // пока есть активные потоки
            try {
                deficit += 1; // мы нуждаемся в уведомлении о завершении потока
                wait(); // ждём уведомления
            } catch (InterruptedException x) {
            }
        }
        while (availableThreadList != null) { // пока список свободных потоков не пуст
            availableThreadList.wakeUp(null); // потоку запрос на завершение
            availableThreadList = availableThreadList.next; //  и исключаем его из списка
        }
    }

    /**
     * Конструктор пула потоков с ограничением на максимальное число активных потоков
     *
     * @param maxThreads максимальное число одновременно работающих потоков.
     */
    public ThreadPool2(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * Конструктор пула потоков с неограниченным числом потоков
     */
    public ThreadPool2() {
        this(Integer.MAX_VALUE);
    }


    private PooledThread availableThreadList; // список свободных потоков
    private int nActiveThreads; // число активных потоков
    private int deficit; // количество потоков, заинтересованных в получении
    // уведомления о завершении работы

    private int maxThreads; // ограничение на максимальное число одновременно
    // работающих потоков

    private boolean closed; // флаг прекращения работы

    static ThreadPool2 theInstance = new ThreadPool2();
}