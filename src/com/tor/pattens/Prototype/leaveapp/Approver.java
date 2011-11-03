package com.tor.pattens.Prototype.leaveapp;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 16:07:19
 * To change this template use File | Settings | File Templates.
 */
public class Approver {
    private static final Logger log = Logger.getLogger(Approver.class);
    private String name;
    private String designation;

    public Approver(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    public String toString() {
        return "[Approver: " + name + "," + designation + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
