package obuchenie.jmx_exemple;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 12:46:30
 * To change this template use File | Settings | File Templates.
 */
public class PingPong extends Thread {
    private static final Logger log = Logger.getLogger(PingPong.class);
    String word;                 // what word to print
    int delay;                   // how long to pause
    int count;                   // number of iterations

  public  PingPong(String What, int Time, int number) {
        word = What;
        delay = Time;
        count = number;
        setName(What);
    }

    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(i + ": " + word + ":" + activeCount());
                sleep(delay);    // wait until next time
            }
        } catch (InterruptedException e) {
            return;             // end this thread
        }
    }
}
