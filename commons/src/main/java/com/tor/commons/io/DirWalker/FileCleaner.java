package com.tor.commons.io.DirWalker;

import org.apache.commons.io.DirectoryWalker;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.05.2010
 * Time: 15:04:11
 * To change this template use File | Settings | File Templates.
 */
public class FileCleaner extends DirectoryWalker {
    private static final Logger log = Logger.getLogger(FileCleaner.class);

    public FileCleaner() {
        super();
    }

    public List clean(File startDirectory) {
        List results = new ArrayList();
        try {
            walk(startDirectory, results);
        } catch (IOException e) {
            e.printStackTrace();
            //log.error(e);
        }
        return results;
    }

    protected boolean handleDirectory(File directory, int depth, Collection results) {
        // delete svn directories and then skip
        if (".svn".equals(directory.getName())) {
            directory.delete();
            return false;
        } else {
            return true;
        }

    }

    protected void handleFile(File file, int depth, Collection results) {
        // delete file and add to list of deleted
        file.delete();
        results.add(file);
    }
}
