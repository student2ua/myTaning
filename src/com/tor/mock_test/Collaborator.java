package com.tor.mock_test;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 15:20:27
 * To change this template use File | Settings | File Templates.
 */
public class Collaborator implements ICollaborator {
    private static final Logger log = Logger.getLogger(Collaborator.class);

    public Collaborator() {
        //конструктор без аргументов
    }

    public String executeJob() {
        return "success";
    }
}
