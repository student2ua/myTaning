package obuchenie.Un_use_varl;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 17.09.2009
 * Time: 15:12:10
 * To change this template use File | Settings | File Templates.
 */
class Ball implements Runnable {
    private static int thread = 0;
    private int threadNumber = ++thread;

    public Ball() {
        
        System.out.println("Start threadNumber = " + threadNumber);
    }

    public void run() {
        int add = 0; System.out.println("Run threadNumber = " + threadNumber);
        for (int idx = 1; idx <= 1000000; idx++) {
            add++;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Complite threadNumber = " + threadNumber);
    }
}
