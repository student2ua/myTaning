package obuchenie.javaee.vo;

import obuchenie.javaee.beans.DirtyMarker;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 18:38:19
 * To change this template use File | Settings | File Templates.
 */
public class SkillSetVO implements DirtyMarker, java.io.Serializable {
    private String skillName;
    private String expertiseLevel;
    private String info;

    // dirty flag
    private boolean dirty = false;

    // new flag
    private boolean isnew = true;

    // deleted flag
    private boolean deleted = false;

    public SkillSetVO() {
        // initialization
        // is new VO
        setNew();
    }

    // get, set and other methods for SkillSet
    // all set methods and modifier methods
    // must call setDirty()
    public void setSkillName(String newSkillName) {
        skillName = newSkillName;
        setDirty();
    }

    public void setInfo(String info) {
        this.info = info;
        setDirty();
    }

    public void setExpertiseLevel(String expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
        setDirty();
    }

    // DirtyMarker methods
    // used for modified value objects only
    public void setDirty() {
        dirty = true;
    }

    public void resetDirty() {
        dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }

    // used for new value objects only
    public void setNew() {
        isnew = true;
    }

    public void resetNew() {
        isnew = false;
    }

    public boolean isNew() {
        return isnew;
    }

    // used for deleted objects only
    public void setDeleted() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void resetDeleted() {
        deleted = false;
    }
}
