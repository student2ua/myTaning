package com.tor.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 19.05.2010
 * Time: 14:04:01
 * To change this template use File | Settings | File Templates.
 */
public class Pinger {
    private static final Logger log = Logger.getLogger(Pinger.class);

    public static void main(String args[]) throws IOException {
        // create the ping command as a list of strings
        Pinger ping = new Pinger();
        List commands = new ArrayList();
        commands.add("ping");
        commands.add("-n");
        commands.add("5");
        // commands.add("www.google.com");
        commands.add("192.168.0.1");
        String[] exc = new String[commands.size()];
        for (int it = 0; it < commands.size(); it++) {
            exc[it] = (String) commands.get(it);
        }
        ping.doCommand(exc);
    }

    /**
     * Provide the command you want to run as a List of Strings. Here's an example:
     * <p/>
     * List<String> commands = new ArrayList<String>();
     * commands.add("/sbin/ping");
     * commands.add("-c");
     * commands.add("5");
     * commands.add("www.google.com");
     * exec.doCommand(commands);
     *
     * @param command The command you want to execute, provided as List<String>.
     * @throws IOException This exception is thrown so you will know about it, and can deal with it.
     */
    public void doCommand(String[] command) throws IOException {
        String s = null;

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);

        // ProcessBuilder pb = new ProcessBuilder(command);
        //Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "CP866"));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s + "|");
            if ("ѕревышен интервал ожидани€ дл€ запроса.".equalsIgnoreCase(s)) {
                System.out.println("не пингуетс€");
            }
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);

        }
    }
}
