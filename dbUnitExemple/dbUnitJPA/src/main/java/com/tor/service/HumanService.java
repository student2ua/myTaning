package com.tor.service;

import com.tor.jpa.Human;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: tor
 * Date: 09.07.14
 * Time: 17:51
 */
public class HumanService {
    private EntityManager manager = Persistence
            .createEntityManagerFactory("DBUnitEx")
            .createEntityManager();

    public void save(Human human) {
        manager.getTransaction().begin();
        manager.persist(human);
        manager.getTransaction().commit();
    }

    public void delete(Human human) {
        manager.getTransaction().begin();
        manager.remove(human);
        manager.getTransaction().commit();
    }

    public Human get(int id) {
        return manager.find(Human.class, id);
    }

    public void update(Human human) {
        manager.getTransaction().begin();
        manager.merge(human);
        manager.getTransaction().commit();
    }

    public List<Human> getAll() {
        TypedQuery<Human> typedQuery = manager.createNamedQuery("Human.getAll", Human.class);
        return typedQuery.getResultList();
    }
}
