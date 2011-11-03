package obuchenie.javaee.beans;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 18:41:41
 * To change this template use File | Settings | File Templates.
 */
public interface DirtyMarker {
    // DirtyMarker methods
    // used for modified value objects only
    void setDirty();

    void resetDirty();

    boolean isDirty();
}
