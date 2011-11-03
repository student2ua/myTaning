package obuchenie.dontUndestend.pattens.decorator;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.10.2009
 * Time: 18:15:23
 * To change this template use File | Settings | File Templates.
 */
import java.lang.reflect.InvocationHandler;
public abstract class Decorator implements InvocationHandler {
   // The InvocationHandler interface defines one method:
   // invoke(Object proxy, Method method, Object[] args). That
   // method must be implemented by concrete (meaning not
   // abstract) extensions of this class.
   private Object decorated;
   protected Decorator(Object decorated) {
      this.decorated = decorated;
   }
   protected synchronized Object getDecorated() {
      return decorated;
   }
   protected synchronized void setDecorated(Object decorated) {
      this.decorated = decorated;
   }
}


