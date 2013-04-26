package com.tor.commons.io.DirWalker;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.05.2010
 * Time: 15:09:30
 * For example, if you wanted all directories which are not hidden and files which end in ".txt":
 */
public class FooDirectoryWalker extends DirectoryWalker {
    private static final Logger log = Logger.getLogger(FooDirectoryWalker.class);

    public FooDirectoryWalker(FileFilter filter) {
        super(filter, -1);
    }

    public FooDirectoryWalker(IOFileFilter dirFilter, IOFileFilter fileFilter) {
        super(dirFilter, fileFilter, -1);
    }

    // Build up the filters and create the walker
    // Create a filter for Non-hidden directories
    IOFileFilter fooDirFilter = FileFilterUtils.andFileFilter(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

    // Create a filter for Files ending in ".txt"
    IOFileFilter fooFileFilter = FileFilterUtils.andFileFilter(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt"));

    // Combine the directory and file filters using an OR condition
    java.io.FileFilter fooFilter = FileFilterUtils.orFileFilter(fooDirFilter, fooFileFilter);

    // Use the filter to construct a DirectoryWalker implementation
    FooDirectoryWalker walker = new FooDirectoryWalker(fooFilter);

    /**
     * This example provides a public cancel() method that can be called by another thread to stop the processing.
     * A typical example use-case would be a cancel button on a GUI. Calling this method sets a volatile flag to ensure
     * it will work properly in a multi-threaded environment. The flag is returned by the handleIsCancelled() method,
     * which will cause the walk to stop immediately. The handleCancelled() method will be the next, and last, callback
     * method received once cancellation has occurred.
     */
    private volatile boolean cancelled = false;

    public void cancel() {
        cancelled = true;
    }

    protected boolean handleIsCancelled(File file, int depth, Collection results) {
        return cancelled;
    }

    protected void handleCancelled(File startDirectory, Collection results, CancelException cancel) {
        // implement processing required when a cancellation occurs
    }
}
