package com.jivesoftware.appsnapplugin.action;

import com.jivesoftware.appsnapplugin.impl.AppsnapImpl;
import com.jivesoftware.appsnapplugin.util.AppsnapConstants;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.community.entitlements.authorization.AdminPage;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@AdminPage("appsnap-capture")
public class RunAppSnapScriptAction extends AdminActionSupport implements Serializable {
    Logger log = Logger.getLogger(RunAppSnapScriptAction.class);
    AppsnapImpl impl = new AppsnapImpl();   //Should/will initialize this from spring

    String appsnapcount = "";
    String appsnapinterval = "";
    String fileName = "";
    String executeStatus = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAppsnapcount(String appsnapcount) {
        log.debug("setting setAppsnapcount " + appsnapcount);
        this.appsnapcount = appsnapcount;
    }

    public void setAppsnapinterval(String appsnapinterval) {
        log.debug("setting setAppsnapinterval " + appsnapinterval);
        this.appsnapinterval = appsnapinterval;
    }

    public String executeAppSnap() {
        Process process = null;
        String shellScript = "appsnap";
        //Check if /usr/local/jive/bin dir exist, return an error if otherwise
        final File executorDirectory = new File(AppsnapConstants.scriptDir);
        if (!executorDirectory.exists()) {
            executeStatus = "no-dir";
            log.error("executorDirectory does not exist..stopping execution");
            return executorDirectory + "does not exist";
        }
        //Check if the actual script exist in the execute dir, return an error if otherwise.
        log.info("appsnapScript " + AppsnapConstants.scriptDir + shellScript);
        final File appsnapScript = new File(AppsnapConstants.scriptDir + shellScript);
        if (!appsnapScript.exists() || !appsnapScript.isFile()) {
            executeStatus = "no-script";
            log.error("executorDirectory exists, but the appsnap script is not found..stopping execution");
            return appsnapScript + "does not exist";

        }
        //set defaults
        if (appsnapinterval == null || appsnapinterval == "") {
            appsnapinterval = "2";
        }
        if (appsnapcount == null || appsnapcount == "") {
            appsnapcount = "5";
        }
        //usr/local/jive/bin/appsnap -i 2 -c 5 -o appsnap.out

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", shellScript, "-i", appsnapinterval, "-c", appsnapcount, "-o", AppsnapConstants.appsnapDirPath + impl.currentDate() + "_appsnap.out");
            processBuilder.directory(executorDirectory);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();

            InputStream processError = process.getErrorStream();
            InputStreamReader isr = new InputStreamReader(processError);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                line += "\n";

            }
            log.error(line);

        } catch (IOException ex) {
            log.error("IOException occured in executeAppSnap " + ex);
        } catch (Exception ex) {
            log.error("Error occured in executeAppSnap " + ex);

        } finally {
            try {
                int shellExitStatus = process.waitFor();
                log.info("ShellExistStatus is " + shellExitStatus);

                if (shellExitStatus != 0 && shellExitStatus != 253) {

                    executeStatus = "exit-status";

                    log.error("Unexpected shell exit status- This means the appsnap script itself failed to execute successfully." +
                            "SSH to the node and ensure that the script has not been tampered with by running: sh /usr/local/jive/bin/appsnap -i 2 -c 5 -o appsnap.out ");

                    // wait for 3 seconds and then destroy the process
                    // Thread.sleep(30000);
                    process.destroy();

                    return "Failed to run script for unknown reasons";
                    //  throw new RuntimeException("Could not run shell script RuntimeException " +scriptDir+shellScript);
                } else {
                    executeStatus = "success";
                }
            } catch (InterruptedException ex) {
                log.error("Shell Script process was interrupted" + ex);
            } catch (Exception ex) {
                log.error("Shell Script process was interrupted" + ex);
            }

            try {
                if (process != null) {
                    process.getInputStream().close();
                }

            } catch (Exception ex) {
                log.warn("Cannot close process.getInputStream() - could cause memory leak" + ex);
            }
            try {
                if (process != null) {
                    process.getOutputStream().close();
                }
            } catch (Exception ex) {
                log.warn("Cannot close process.getInputStream() - could cause memory leak" + ex);

            }
            try {
                if (process != null) {
                    process.getErrorStream().close();
                }
            } catch (Exception ex) {
                log.warn("Cannot close process.getErrorStream() - could cause memory leak" + ex);

            }

        }

        return executeStatus;
    }

    public String getExecuteStatus() {
        log.trace("Execute status is " + executeStatus);
        return executeStatus;
    }

    public ArrayList getFilelist() {
        ArrayList al = new ArrayList();
        try {
            File dir = new File(AppsnapConstants.appsnapDirPath);
            String[] appsnaps = dir.list();
            if (appsnaps == null) {
                log.debug("no appsnap exist");
            } else {
                al.addAll(Arrays.asList(appsnaps));
                //Sort the files in desceding order of creationdate
                Collections.sort(al, Collections.reverseOrder());
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return al;
    }

    public Map getMapfiles() {
        ArrayList al = new ArrayList();
        File dir = new File(AppsnapConstants.appsnapDirPath);
        Map<String, Object> data = new HashMap<String, Object>();
        for (File file : dir.listFiles()) {

            data.put(dir.getName(), dir.lastModified());

        }
        return data;
    }

    public String doDeleteFile() {

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
            log.error("Appsnap deletion failed " + ex);
        }
        return SUCCESS;

    }

    public long getTidyHouseProperty() {
        return JiveGlobals.getJiveLongProperty(AppsnapConstants.TIDY_HOUSE_VALUE_PROPERTY, AppsnapConstants.tidyHouseDefaultValue);
    }

    @Override
    public String execute() {

        log.info("In the Action class for the RunAppsnapScriptAction");
        // 1. Create appsnap.out dir - if it does not  exist
        impl.createStorageDir();
        log.debug("createStorageDir() successfully executed");
        //2. Execute the appsnap.sh script
        executeAppSnap();
        log.debug("executeAppSnap() successfully executed");

        return SUCCESS;
    }


}
