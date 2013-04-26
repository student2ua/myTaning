package com.tor.commons.io.DirWalker;

import org.apache.commons.io.DirectoryWalker;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.05.2010
 * Time: 15:21:06
 * This shows an example of how internal cancellation processing could be implemented. Note the decision logic
 * and throwing a DirectoryWalker.CancelException could be implemented in any of the lifecycle methods.
 */
public class BarDirectoryWalker extends DirectoryWalker {
    private static final Logger log = Logger.getLogger(BarDirectoryWalker.class);

    protected boolean handleDirectory(File directory, int depth, Collection results) throws IOException {
        // cancel if hidden directory
        if (directory.isHidden()) {
            throw new CancelException(directory, depth);
        }
        return true;
    }

    protected void handleFile(File file, int depth, Collection results) throws IOException {
        // cancel if read-only file
        if (!file.canWrite()) {
            throw new CancelException(file, depth);
        }
        results.add(file);
    }

    protected void handleCancelled(File startDirectory, Collection results, CancelException cancel) {
        // implement processing required when a cancellation occurs
    }
}
