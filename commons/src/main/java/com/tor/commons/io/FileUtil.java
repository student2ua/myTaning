package com.tor.commons.io;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.04.2009
 * Time: 13:56:07
 */
public class FileUtil {
    /** не юзать хюня какато*/
    public void moveConfig2Dir(String inDir,String outDir) throws IOException {
        Collection listConfigFile=getListOfAllConfigFiles(inDir);
        File outPath=new File(outDir);
        for(Iterator it=listConfigFile.iterator();it.hasNext();){
            try {
                FileUtils.copyFileToDirectory((File)it,outPath);
              
                FileUtils.forceDelete((File)it);
            } catch (IOException e) {
            e.printStackTrace();
                throw e;
            }
        }
    }
    public void deleteDirectory(String dir2del) {
        try {
            FileUtils.deleteDirectory(new File(dir2del));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printFreeSpeace(String path){
        try {
            //long freeSpace = FileSystemUtils.freeSpace("C:/");
            long freeSpace = FileSystemUtils.freeSpaceKb(path);
            System.out.println(path+" freeSpace = " + freeSpace);
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    private boolean mkdir(String parentDir, String directoryName) {
        String fullPath = parentDir + directoryName;
        File directory = new File(fullPath);
        return directory.mkdir();
    }

    private Collection getListOfAllConfigFiles(String dir2get) {
        return FileUtils.listFiles(new File(dir2get), new WildcardFileFilter("*.cfg"), null);
    }
}
