package obuchenie.javaee.dao;

import obuchenie.javaee.vo.SkillSetVO;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 18:47:02
 * To change this template use File | Settings | File Templates.
 */
public interface SkillSetDAO {
    void insert(SkillSetVO vo);

    boolean delete(SkillSetVO vo);

    boolean update(SkillSetVO skill);
}
