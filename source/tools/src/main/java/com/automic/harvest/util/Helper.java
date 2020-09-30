package com.automic.harvest.util;

import java.io.File;

import com.automic.harvest.exception.AutomicException;
import com.ca.cmsdk.CmsdkFileException;
import com.ca.cmsdk.fileAgent.FileAgent;
import com.ca.cmsdk.fileAgent.FileSystemItem;
import com.ca.cmsdk.lists.StringList;

/**
 * @author patak02
 * 
 */
public class Helper {

    /**
     * Recursively get the list of files with there absolute path
     * 
     * @param agent
     *            FileAgent Harvest File Agent
     * @param recursive
     *            Boolean If true then recursive to the sub directories
     * @param filter
     *            String Comma separated file list to filter
     * @return StringList List of files
     * @throws AutomicException
     * @throws CmsdkFileException
     */
    public static StringList getFileListByAgent(final FileAgent agent, final Boolean recursive, final String[] filter)
            throws AutomicException, CmsdkFileException {
        StringList files = new StringList();
        // If filter is specified then we need to search them instead of searching all files/folders
        if (filter != null && filter.length > 0) {
            for (String file : filter) {
                if (!file.trim().isEmpty()) {
                    String editedFilePath = file.trim().replace('/', '\\');
                    if (isSubDirByAgent(agent, editedFilePath)) {
                        if (recursive) {
                            String dirPath = agent.getCurrentDir() + File.separator + editedFilePath;
                            FileSystemItem dirPathItem = agent.getDirectory(dirPath);
                            FileSystemItem[] fileItems = agent.getFiles(dirPathItem, recursive);
                            for (FileSystemItem fItem : fileItems) {
                                String absFilePath = fItem.getFullPathName();
                                files.add(absFilePath);
                            }
                        }
                    } else {
                        String filePath = agent.getCurrentDir() + File.separator + editedFilePath;
                        files.add(filePath);
                    }
                }
            }
        } else {
            FileSystemItem[] fileitems = agent.getFiles(recursive);
            if (fileitems != null && fileitems.length > 0) {
                for (FileSystemItem fileitem : fileitems) {
                    String fullpath = fileitem.getFullPathName();
                    files.add(fullpath);
                }

            } else {
                throw new AutomicException("No files found in client directory [" + agent.getCurrentDir() + "].");
            }

        }
        return files;
    }

    /**
     * Checks if the passed relative path is a sub directory of File Agent's current working directory
     * 
     * @param agent
     *            File Agent
     * @param relateivePath
     *            Relative file path
     * @return True/False
     * @throws CmsdkFileException
     */
    public static boolean isSubDirByAgent(final FileAgent agent, final String relateivePath) throws CmsdkFileException {
        FileSystemItem[] dirItems = agent.getDirectories();
        String strPath = agent.getCurrentDir() + File.separator + relateivePath;
        String editedStrPath = strPath.replace('/', '\\');
        for (FileSystemItem item : dirItems) {
            String strItem = item.getFullPathName().replace('/', '\\');
            if (strItem.equalsIgnoreCase(editedStrPath)) {
                return true;
            }
        }
        return false;
    }

}
