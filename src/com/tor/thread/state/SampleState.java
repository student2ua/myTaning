package com.tor.thread.state;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 19.09.13
 * Time: 16:34
 */
public class SampleState implements Runnable {
    enum StateTask {IDLE, PROGRESS, SUCCESS, FAILED}

    private StateTask threadState = StateTask.IDLE;
    private Exception exception = null;

    /**
     * Default constructor
     */
    public SampleState() {
        super();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        threadState = StateTask.PROGRESS;
        try {
            doTask();
            threadState = StateTask.SUCCESS;
        } catch (Exception e) {
            threadState = StateTask.FAILED;
            exception = e;
        }
    }

    /**
     * @throws Exception
     */
    private void doTask() throws Exception {
        Thread.sleep(500L);
        throw new Exception("Exception in the thread");

    }

    /**
     * @return Returns the exception.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @return Returns the threadState.
     */
    public StateTask getThreadState() {
        return threadState;
    }

    /**
     * TEST
     */
    public static void main(String[] args) {
        SampleState sample = new SampleState();
        System.out.println(sample.getThreadState());
        new Thread(sample).start();
        try {
            Thread.sleep(100L);
        } catch (Exception ignore) {
        }
        System.out.println(sample.getThreadState());
        try {
            Thread.sleep(1000L);
        } catch (Exception ignore) {
        }
        System.out.println(sample.getThreadState());
        System.out.println("Thread exception: " + sample.getException());
    }
}