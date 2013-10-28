package com.jivesoftware.appsnapplugin.impl;

import com.jivesoftware.appsnapplugin.util.AppsnapConstants;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: israel.ogbole
 * Date: 10/8/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppsnapImpl {
    Logger log = Logger.getLogger(AppsnapImpl.class);


    public void createStorageDir() {
        //intended to run only once
        // JiveHome/plugins/appsnaplugin
        File dir = new File(AppsnapConstants.appsnapDirPath);
        try {
            if (!dir.exists()) {
                log.trace(" about to create appsnaps storage dir");
                dir.mkdirs();
                log.trace("Created Appnsap Storage dir " + dir);

            } else {
                log.debug("Dir Already exist.. skipping creation " + dir);
            }

        } catch (Exception ex) {
            log.error("createStorageDir failed:" + ex.getStackTrace());

        }

    }

    //This method has been deprecated, will leave it here for now till java 7 when I can get file creation date.
    //Will be useful in Jive 7 version of this plugin
    //This method takes a backup of appsnap.out- concatinanates with system timestamp
    public void checkIfAppsnapDotOutExists() {

        File file = new File(AppsnapConstants.appsnapFilePath);
        try {
            if (file.exists() && file.isFile() && file.canRead()) {
                log.debug("file exists " + file.getName());
                //thinking of deleteOnExist.. not sure if it's the best approach for now. so I wanna take backups.
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                Date backupdate = new Date();
                //   File newfile = new File(appsnapFilePath + "_" + dateFormat.format(backupdate) + ".bk");
                File newfile = new File(AppsnapConstants.appsnapFilePath + "_" + dateFormat.format(backupdate));
                //take backup of appsnap.out
                file.renameTo(newfile);
                log.info("file renamed to " + newfile);

            } else {
                log.info("checkIfAppsnapDotOutExists - no files exist");

            }
        } catch (Exception ex) {
            log.error("Error occured in checkIfAppsnapDotOutExists " + ex.getStackTrace());
            ex.printStackTrace();
        }

    }

    //Delete  old appsnaps -takes no. of days and fileextention as parameters
    public void tidyHouse(long days, String fileExtension) {

        File folder = new File(AppsnapConstants.appsnapDirPath);
        try {
            if (folder.exists()) {
                File[] listFiles = folder.listFiles();
                long eligibleForDeletion = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);
                for (File listFile : listFiles) {
                    // !listFile.getName().endsWith(fileExtension) -- Dont delete .out
                    if (listFile.getName().endsWith(fileExtension) && listFile.lastModified() < eligibleForDeletion) {
                        if (!listFile.delete()) {
                            log.error("tidyHouse: Unable to Delete Files..No cause known");
                            //listFile.delete();
                        }
                    } else {

                        log.trace("tidyHouse : No file is eligible for deletion. no. of days is " + days + " and fileExtention " + fileExtension);

                    }
                }

            }

        } catch (Exception ex) {
            log.error("Error occured in tidyHouse " + ex.getStackTrace());
            //ex.printStackTrace();
        }
    }


    public String currentDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        return dateFormat.format(new Date());


    }


}

