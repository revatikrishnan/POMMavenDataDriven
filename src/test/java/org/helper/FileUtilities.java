package org.helper;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.helper.GlobalImpl;

public class FileUtilities {

    public void deleteAllFilesFromScreenshot() {
        
        if (GlobalImpl.screenshots.exists())
            if (GlobalImpl.screenshots.isDirectory()){
             try {
                 FileUtils.cleanDirectory(GlobalImpl.screenshots);
                 System.out.println("All Screenshots cleared");
             }
             catch (IOException e) {
                 
             }
            }
        
     }
}
