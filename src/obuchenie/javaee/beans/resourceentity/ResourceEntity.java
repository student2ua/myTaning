package obuchenie.javaee.beans.resourceentity;

import obuchenie.javaee.dao.SkillSetDAO;
import obuchenie.javaee.exceptions.ResourceException;
import obuchenie.javaee.exceptions.SkillSetException;
import obuchenie.javaee.vo.ResourceDetailsVO;
import obuchenie.javaee.vo.ResourceVO;
import obuchenie.javaee.vo.SkillSetVO;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 16:35:34
 * Multiple Value Objects Strategy Ð Resource Entity Bean
 */
public class ResourceEntity implements EntityBean {

    private static final Logger log = Logger.getLogger(ResourceEntity.class);
    // entity bean attributes
    public String employeeId;
    public String lastName;
    public String firstName;
    public String departmentId;
    public String practiceGroup;
    public String title;
    public String grade;
    public String email;
    public String phone;
    public String cell;
    public String pager;
    public String managerId;

    // Collection of BlockOutTime Dependent objects
    public Collection blockoutTimes;

    // Collection of SkillSet Dependent objects
    public Collection skillSets;
    // entity bean business methods

    // Multiple Value Object method : Get ResourceVO

    public ResourceVO getResourceData() {

        // create new ResourceVO instance and copy
        // attribute values from entity bean into VO
        return createResourceVO();
    }

    // Multiple Value Object method : Get
    // ResourceDetailsVO
    public ResourceDetailsVO getResourceDetailsData() {

        // create new ResourceDetailsVO instance and copy
        // attribute values from entity bean into VO
        return createResourceDetailsVO();
    }

    // other entity bean methods

    private EntityContext context;

    // Entity Bean methods implementation
    public String ejbCreate(ResourceVO resource) throws CreateException {
        try {
            this.employeeId = resource.employeeId;
            setResourceData(resource);
            getResourceDAO().create(resource);
        } catch (Exception ex) {
            throw new EJBException("Reason:" + ex);
        }
        return this.employeeId;
    }

    public String ejbFindByPrimaryKey(String primaryKey) throws FinderException {
        boolean result;
        try {
            ResourceDAO resourceDAO = getResourceDAO();
            result = resourceDAO.selectByPrimaryKey(primaryKey);
        } catch (Exception ex) {
            throw new EJBException("Reason:" + ex);
        }
        if (result) {
            return primaryKey;
        } else {
            throw new ObjectNotFoundException(primaryKey);
        }
    }

    public void ejbRemove() {
        try {
            // Remove dependent objects
            if (this.skillSets != null) {

                SkillSetDAO skillSetDAO = getSkillSetDAO();
                skillSetDAO.setResourceID(employeeId);
                skillSetDAO.deleteAll();
                skillSets = null;
            }
            if (this.blockoutTime != null) {
                BlockOutTimeDAO blockouttimeDAO = getBlockOutTimeDAO();
                blockouttimeDAO.setResourceID(employeeId);
                blockouttimeDAO.deleteAll();
                blockOutTimes = null;
            }

            // Remove the resource from the persistent store
            ResourceDAO resourceDAO = new ResourceDAO(employeeId);
            resourceDAO.delete();
        } catch (ResourceException ex) {
            throw new EJBException("Reason:" + ex);
        } catch (BlockOutTimeException ex) {
            throw new EJBException("Reason:" + ex);
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    public void setEntityContext(EntityContext context) {
        this.context = context;
    }

    public void unsetEntityContext() {
        context = null;
    }

    public void ejbActivate() {
        employeeId = (String) context.getPrimaryKey();
    }

    public void ejbPassivate() {
        employeeId = null;
    }

    public void ejbLoadWhithOutLazzy() {
        try {
            // load the resource info from
            ResourceDAO resourceDAO = getResourceDAO();
            setResourceData((ResourceVO) resourceDAO.load(employeeId));

            // Load other dependent objects, if necessary
        } catch (Exception ex) {
            throw new EJBException("Reason:" + ex);
        }
    }

    public void ejbLoad() {
        try {
            // load the resource info from
            ResourceDAO resourceDAO = new ResourceDAO(employeeId);
            setResourceData((ResourceVO) resourceDAO.load());

            // If the lazy loaded objects are already
            // loaded, they need to be reloaded.
            // If there are not loaded, do not load them
            // here...lazy load will load them later.
            if (skillSets != null) {
                reloadSkillSets();
            }
            if (blockOutTimes != null) {
                reloadBlockOutTimes();
            }
        } catch (Error e) {
            throw new EJBException("Reason:" + e);
        }
    }

    public void ejbStoreNotOptimazed() {
        try {
            // Store resource information
            getResourceDAO().update(getResourceData());

            // Store dependent objects as needed
        } catch (SkillSetException ex) {
            throw new EJBException("Reason:" + ex);
        } catch (BlockOutTimeException ex) {
            throw new EJBException("Reason:" + ex);
        }
    }

    /**
     * Implementing Store Optimization
     */
    public void ejbStore() {
        try {
            // Load the mandatory data
            getResourceDAO().update(getResourceData());

            // Store optimization for dependent objects
            // check dirty and store
            // Check and store commitments
            if (skillSets != null) {
                // Get the DAO to use to store
                SkillSetDAO skillSetDAO = getSkillSetDAO();
                Iterator skillIter = skillSet.iterator();
                while (skillIter.hasNext()) {
                    SkillSetVO skill = (SkillSetVO) skillIter.next();
                    if (skill.isNew()) {
                        // This is a new dependent, insert it
                        skillSetDAO.insert(skill);
                        skill.resetNew();
                        skill.resetDirty();
                    } else if (skill.isDeleted()) {
                        // delete Skill
                        skillSetDAO.delete(skill);
                        // Remove from dependents list
                        skillSets.remove(skill);
                    } else if (skill.isDirty()) {
                        // Store Skill, it has been modified
                        skillSetDAO.update(skill);
                        // Saved, reset dirty.
                        skill.resetDirty();
                        skill.resetNew();
                    }
                }
            }

            // Similarly, implement store optimization
            // for other dependent objects such as

            // BlockOutTime, ...
            ...
        } catch (SkillSetException ex) {
            throw new EJBException("Reason:" +...);
        } catch (BlockOutTimeException ex) {
            throw new EJBException("Reason:" +...);
        } catch (CommitmentException ex) {
            throw new EJBException("Reason:" +...);
        }
    }

    public void ejbPostCreate(ResourceVO resource) {
    }

    // Method to Get Resource value object
    public ResourceVO getResourceVO() {
        // create a new Resource value object
        ResourceVO resourceVO = new ResourceVO(employeeId);

        // copy all values
        resourceVO.lastName = lastName;
        resourceVO.firstName = firstName;
        resourceVO.departmentId = departmentId;
        return resourceVO;
    }

    public void setResourceData(ResourceVO resourceVO) {
        // copy values from value object into entity bean
        employeeId = resourceVO.employeeId;
        lastName = resourceVO.lastName;
        ...
    }

    // Method to get dependent value objects
    public Collection getSkillSetsData() throws SkillSetException {
        // If skillSets is not loaded, load it first.
        // See Lazy Load strategy implementation.

        checkSkillSetLoad();

        return skillSets;
    }

    private void checkSkillSetLoad() throws SkillSetException {
        try {
            // Lazy Load strategy...Load on demand
            if (skillSets == null)
                skillSets = getSkillSetDAO(resourceId).loadAll();
        } catch (Exception exception) {
            // No skills, throw an exception
            throw new SkillSetException("Error in Lazy Load" + exception);
        }
    }

    // other get and set methods as needed

    // Entity bean business methods

    public void addBlockOutTimes(Collection moreBOTs)
            throws BlockOutTimeException {
        // Note: moreBOTs is a collection of
        // BlockOutTimeVO objects
        try {
            Iterator moreIter = moreBOTs.iterator();
            while (moreIter.hasNext()) {
                BlockOutTimeVO botVO = (BlockOutTimeVO)
                        moreIter.next();
                if (!(blockOutTimeExists(botVO))) {
                    // add BlockOutTimeVO to collection
                    botVO.setNew();
                    blockOutTime.add(botVO);
                } else {
                    // BlockOutTimeVO already exists, cannot add
                    throw new BlockOutTimeException(...);
                }
            }
        } catch (Exception exception) {
            throw new EJBException(...);
        }
    }

    public void addSkillSet(Collection moreSkills) throws SkillSetException {
        // similar to addBlockOutTime() implementation
        ...
    }


    public void updateBlockOutTime(Collection updBOTs) throws BlockOutTimeException {
        try {
            Iterator botIter = blockOutTimes.iterator();
            Iterator updIter = updBOTs.iterator();
            while (updIter.hasNext()) {
                BlockOutTimeVO botVO = (BlockOutTimeVO)
                        updIter.next();
                while (botIter.hasNext()) {
                    BlockOutTimeVO existingBOT =
                            (BlockOutTimeVO) botIter.next();
                    // compare key values to locate BlockOutTime
                    if (existingBOT.equals(botVO)) {
                        // Found BlockOutTime in collection
                        // replace old BlockOutTimeVO with new one
                        botVO.setDirty(); //modified old dependent
                        botVO.resetNew(); //not a new dependent
                        existingBOT = botVO;
                    }
                }
            }
        } catch (Exception exc) {
            throw new EJBException(...);
        }
    }

    public void updateSkillSet(Collection updSkills) throws CommitmentException {
        // similar to updateBlockOutTime...
        ...
    }

}
