package obuchenie.Un_use_varl;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 17.09.2009
 * Time: 12:46:11
 * To change this template use File | Settings | File Templates.
 */

/**
 * ��� �������
 */
public class ThreadPool2 {
    /**
     * �������� ��������� ���� �������
     */
    public static ThreadPool2 getInstance() {
        return theInstance;
    }

    /**
     * ����� ��� ���������� �������������
     */
    static class PooledThread extends Thread {
        PooledThread next;
        Object ready; // ������, ������������ ��� �������� ������ �������
        Object done;  // ������, ������������ ��� ������������ ���������� ������
        boolean busy; // ������ ����������, ��� ����� �����
        boolean doneNotificationNeeded; // ��� �� ���-������ ���������� ������
        Runnable task; // ��� ����� �������
        ThreadPool2 pool2; // ��� �������


        public void run() {
            try {
                synchronized (ready) { // ��������� ready � ��������� � �������� ������
                    while (!pool2.closed) { // ��������� �� ������ �� ���
                        synchronized (done) { // ���������� ready
                            busy = false;    // �� ��������� ������ ������� �� ���������� �������� �����
                            if (doneNotificationNeeded) { // ���� ���-�� ��� ���������� �������
                                done.notify();     // �� ����� �����������, ��� �� � ���������
                            }
                        }
                        ready.wait();              // ��� ������ �������
                        if (task != null) {        // ���� �� ��� ��������
                            task.run();            // �� ���������
                        } else {
                            break;                 // ����� ��������� ������
                        }
                    }
                }
            } catch (InterruptedException x) {
            }
        }

        final void wakeUp(Runnable t) { // ��������� ������� �� ����������
            synchronized (ready) { // ����������� read�, ����� ����� ���� ������� �����������
                busy = true; // ���������� ���� ���������
                doneNotificationNeeded = false;
                task = t;
                ready.notify(); // ������� ����������� ���������� ������
            }
        }

        final void waitCompletion() throws InterruptedException {
            synchronized (done) { // ����������� done ��� ��������
                if (busy) { // ���� ������� ��� �� ������������
                    doneNotificationNeeded = true; // �� ��������� ���� �������� ����������
                    done.wait(); // � ���������
                }
            }
        }

        PooledThread(ThreadPool2 pool2) {
            this.pool2 = pool2;
            ready = new Object();
            done = new Object();
            busy = true;
            setDaemon(true); // ��������� ����� ��� ������, ����� �� ����� ��� ����������
            // ��� ������ �� ���������
            this.start();
        }
    }

    /**
     * ����� ��������� ����� � ��������� � �� ������� �� ����������
     *
     * @param task ������ ����������� ��������� Runnable,
     *             ����� run �������� ����� �������� �������
     * @return �����, ���������� ��� ������� �������
     *         (��� ����� ������������ ������ � ������ ThreadPool2.join)
     */
    public Thread start(Runnable task) {
        PooledThread thread;
        synchronized (this) { // ��� ������ �� ������� ����� ����������
            while (availableThreadList == null) { // ���� ������ ��������� ������� ����
                try {
                    if (nActiveThreads == maxThreads) { // � ���� ���������� ����������� ��
                        // ������������ ����� �������
                        deficit += 1; // �� ��������, ��� ���� ������, ������ ����������
                        wait();  // � ��������� ���� �����-������ �� ������� �� �����������
                    } else {
                        availableThreadList = new PooledThread(this); // ������� ����� �����
                        availableThreadList.waitCompletion(); // � ��������� �������,
                        // ����� �� �������� � �����
                        // ����� � ��������� �������
                    }
                } catch (InterruptedException x) {
                    return null;
                }
            }
            thread = availableThreadList; // ����� ����� �� ������ ���������

            availableThreadList = thread.next;
            nActiveThreads += 1; // �������� ������� �������� �������
        }
        thread.wakeUp(task); // ��������� ������� �� ����������
        return thread;
    }

    /**
     * ��������� ���������� �������. ����� ��� ���� ������������ � ������ ���������
     *
     * @param thread �����, ������������ ������� ThreadPool2.start
     */
    public void join(Thread thread) throws InterruptedException {
        PooledThread t = (PooledThread) thread;
        t.waitCompletion(); // ��������� ���������� �������
        synchronized (this) { // ��� ������ �� ������� ����� ����������
            t.next = availableThreadList; // �������� ����� � ������ ���������
            availableThreadList = t;
            nActiveThreads -= 1; // ��������� �������� �������� �������� �������
            if (deficit > 0) { // ���� ���� ������ ������ ������������
                notify();  // �� ������� ����������� � ���, ��� ����� �����������
                deficit -= 1;
            }
        }
    }

    /**
     * �������� ���� �������. ��������� ��������� ������ ���� �������� ������� �
     * ���������  ��� ��������� ������
     */
    public synchronized void close() {
        closed = true; // ������ ���� ����������� ������
        while (nActiveThreads > 0) { // ���� ���� �������� ������
            try {
                deficit += 1; // �� ��������� � ����������� � ���������� ������
                wait(); // ��� �����������
            } catch (InterruptedException x) {
            }
        }
        while (availableThreadList != null) { // ���� ������ ��������� ������� �� ����
            availableThreadList.wakeUp(null); // ������ ������ �� ����������
            availableThreadList = availableThreadList.next; //  � ��������� ��� �� ������
        }
    }

    /**
     * ����������� ���� ������� � ������������ �� ������������ ����� �������� �������
     *
     * @param maxThreads ������������ ����� ������������ ���������� �������.
     */
    public ThreadPool2(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * ����������� ���� ������� � �������������� ������ �������
     */
    public ThreadPool2() {
        this(Integer.MAX_VALUE);
    }


    private PooledThread availableThreadList; // ������ ��������� �������
    private int nActiveThreads; // ����� �������� �������
    private int deficit; // ���������� �������, ���������������� � ���������
    // ����������� � ���������� ������

    private int maxThreads; // ����������� �� ������������ ����� ������������
    // ���������� �������

    private boolean closed; // ���� ����������� ������

    static ThreadPool2 theInstance = new ThreadPool2();
}