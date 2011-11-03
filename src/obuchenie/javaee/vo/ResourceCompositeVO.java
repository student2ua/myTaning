package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 18:50:04
 * Implementing the Composite Value Object
 */
public class ResourceCompositeVO {
    private static final Logger log = Logger.getLogger(ResourceCompositeVO.class);
    private ResourceVO resourceData;
    private Collection skillSets;
    private Collection blockOutTimes;

    // value object constructors

    public ResourceCompositeVO() {
    }

    public ResourceCompositeVO(ResourceVO resourceData, Collection skillSets, Collection blockOutTimes) {
        this.resourceData = resourceData;
        this.skillSets = skillSets;
        this.blockOutTimes = blockOutTimes;
    }
    // get and set methods

    public ResourceVO getResourceData() {
        return resourceData;
    }

    public void setResourceData(ResourceVO resourceData) {
        this.resourceData = resourceData;
    }

    public Collection getSkillSets() {
        return skillSets;
    }

    public void setSkillSets(Collection skillSets) {
        this.skillSets = skillSets;
    }

    public Collection getBlockOutTimes() {
        return blockOutTimes;
    }

    public void setBlockOutTimes(Collection blockOutTimes) {
        this.blockOutTimes = blockOutTimes;
    }
}
