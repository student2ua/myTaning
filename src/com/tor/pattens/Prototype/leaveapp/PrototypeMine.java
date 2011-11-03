package com.tor.pattens.Prototype.leaveapp;

import org.apache.log4j.Logger;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 16:07:56
 * To change this template use File | Settings | File Templates.
 */
public class PrototypeMine {
    private static final Logger log = Logger.getLogger(PrototypeMine.class);

    public static void main(String[] args) {

        Approver manager = new Approver("Johny", "manager");
        LeaveApplication sickLeave =
                new LeaveApplication("Fever", new Date(2007, 3, 20), new Date(2007, 3, 22), manager);
        System.out.println(sickLeave);

        LeaveApplication casualLeave = (LeaveApplication) sickLeave.clone();
        casualLeave.setReason("Vacation");
        casualLeave.setStartDate(new Date(2007, 10, 10));
        casualLeave.setEndDate(new Date(2007, 10, 20));
        System.out.println(casualLeave);
    }
}
