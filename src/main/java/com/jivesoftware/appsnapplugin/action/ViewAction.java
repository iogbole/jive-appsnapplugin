package com.jivesoftware.appsnapplugin.action;

import com.jivesoftware.appsnapplugin.impl.AppsnapImpl;
import com.jivesoftware.appsnapplugin.util.AppsnapConstants;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.community.entitlements.authorization.AdminPage;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: israel.ogbole
 * Date: 10/8/13
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */

@AdminPage("viewappsnap")
public class ViewAction extends AdminActionSupport implements Serializable {
    Logger log = Logger.getLogger(ViewAction.class);
    AppsnapImpl impl = new AppsnapImpl();   //Should/will initialize this from spring

    String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public ArrayList getFilelist() {
        ArrayList al = new ArrayList();
        File dir = new File(AppsnapConstants.appsnapDirPath);
        String[] appsnaps = dir.list();
        if (appsnaps == null) {
        } else {
            al.addAll(Arrays.asList(appsnaps));
            Collections.sort(al, Collections.reverseOrder());
        }
        return al;
    }


    public String doDelete() {

        try {
            File fname = new File(AppsnapConstants.appsnapDirPath + fileName);

            if (fname.exists() && fname.isFile()) {
                fname.delete();
                log.debug("Appsnap file was deleted");
                addActionMessage("Deleted successfully");
            } else {
                log.debug("failed to delete appsnap");
                addActionError("Failed to delete appsnap");

            }
        } catch (Exception ex) {
            log.error("Appsnap deletion failed " + ex.getMessage());
        }
        return SUCCESS;

    }

    //Currently not in use.. will use this method when it's all good to add some thread dump analysis
    public String getReadDoc() {
        String readDoc = "";
        int read, N = 1024 * 1024; // gain some performance and speed by reading 1MB per time.
        char[] buffer = new char[N];
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(AppsnapConstants.appsnapFilePath);
            br = new BufferedReader(fr);
            if (fr == null) {
                log.error("File Reader must not be NULL ");
                return "error occured";
            }
            while (true) {
                read = br.read(buffer, 0, N);
                readDoc += new String(buffer, 0, read);

                if (read < N) {
                    break;
                }
            }


        } catch (Exception ex) {
            log.error("Cannot read appsnap output " + ex.getStackTrace());
        } finally {
            // releases any system resources associated with the stream
            if (fr != null) {
                try {
                    fr.close();

                } catch (IOException ex) {
                    log.error("fr cannot be closed" + ex.getStackTrace());
                }
            }
            if (br != null) {
                try {

                    br.close();
                } catch (IOException ex) {
                    log.error(" br cannot be closed" + ex.getStackTrace());
                }
            }

        }

        return readDoc;
    }


    public long getTidyHouseProperty() {
        return JiveGlobals.getJiveLongProperty(AppsnapConstants.TIDY_HOUSE_VALUE_PROPERTY, AppsnapConstants.tidyHouseDefaultValue);
    }

    public String getTidyHouseValue() {

        return Long.toString(AppsnapConstants.tidyHouseDefaultValue);

    }

    @Override
    public String execute() {

        log.debug("In viewAction Class of appsnap plugin");

        impl.tidyHouse(getTidyHouseProperty(), ".out");
        log.debug("4. tidyHouse() successfully executed");

        return SUCCESS;
    }

}
