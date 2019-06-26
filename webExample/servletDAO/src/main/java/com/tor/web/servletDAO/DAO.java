package com.tor.web.servletDAO;

import java.util.List;

/**
 * User: tor
 * Date: 26.06.19
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public interface DAO<T, ID> {
    T find(ID id) throws Exception;

    List<T> findAll() throws Exception;

    boolean save(T t) throws Exception;

    boolean update(T t) throws Exception;

    boolean delete(T t) throws Exception;
}
