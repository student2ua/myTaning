package com.tor.sun.tools.attach;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * User: tor
 * Date: 019, 19.09.2023
 * Time: 21:50
 * Maven:system:jdk-tools:jdk
 */
public class AttachApiExample {
    public static void main(String[] args) throws Exception {
        System.out.println("BEGIN-----------------");
        List<VirtualMachineDescriptor> vms = com.sun.tools.attach.VirtualMachine.list();
        for (VirtualMachineDescriptor vm : vms) {
            System.out.println("vm="+vm);
        }

        System.out.println("END--------------------");
    }
}
