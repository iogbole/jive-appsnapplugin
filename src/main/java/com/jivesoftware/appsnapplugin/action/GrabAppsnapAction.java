package com.jivesoftware.appsnapplugin.action;

import com.jivesoftware.appsnapplugin.util.AppsnapConstants;
import com.jivesoftware.community.action.admin.AdminActionSupport;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

//This action class :
// 1. renders the grab appsnap form
// 2. downloads the appsnap file.
public class GrabAppsnapAction extends AdminActionSupport implements Serializable {
    Logger log = Logger.getLogger(GrabAppsnapAction.class);


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String filename;
    private FileInputStream fileInputStream;

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }


    public String grabFile() {
        try {
            log.trace("appsnap Dir  " + AppsnapConstants.appsnapDirPath);
            log.trace("fileName " + filename);
            fileInputStream = new FileInputStream(new File(AppsnapConstants.appsnapDirPath, filename));

        }
        catch (FileNotFoundException ex) {
            log.error(ex);
        }
        catch (Exception ex) {
            log.error(ex);
        }

        return SUCCESS;
    }


    @Override
    public String execute() {

        return SUCCESS;
    }

}
