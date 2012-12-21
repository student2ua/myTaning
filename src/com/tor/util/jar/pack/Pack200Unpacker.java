package com.tor.util.jar.pack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.zip.GZIPInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 07.11.12
 * Time: 13:33
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class Pack200Unpacker {
    public static void main(String[] args) throws Exception {
        String inName = args[0];
        String outName;
        if (inName.endsWith(".pack.gz")) {
            outName = inName.substring(0, inName.length() - 8);
        } else if (inName.endsWith(".pack")) {
            outName = inName.substring(0, inName.length() - 5);
        } else {
            outName = inName + ".unpacked";
        }
        JarOutputStream out;
        InputStream in;
        Pack200.Unpacker unpacker = Pack200.newUnpacker();
        out = new JarOutputStream(new FileOutputStream(outName));
        in = new FileInputStream(inName);
        if (inName.endsWith(".gz"))
            in = new GZIPInputStream(in);
        unpacker.unpack(in, out);
        out.close();
    }
}
