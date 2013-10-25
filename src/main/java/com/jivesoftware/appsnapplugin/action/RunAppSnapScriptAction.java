package com.jivesoftware.appsnapplugin.action;

import com.jivesoftware.appsnapplugin.impl.AppsnapImpl;
import com.jivesoftware.appsnapplugin.util.AppsnapConstants;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.JiveHome;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.community.entitlements.authorization.AdminPage;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: israel.ogbole
 * Date: 10/4/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */


@AdminPage("jvmappsnap")
public class RunAppSnapScriptAction extends AdminActionSupport implements Serializable {
    Logger log = Logger.getLogger(RunAppSnapScriptAction.class);
    AppsnapImpl impl = new AppsnapImpl();   //Should/will initialize this from spring

    // private String captureAppSnap;
    private String readDoc = "";

    String appsnapcount = "";
    String appsnapinterval = "";
    String fileName = "";

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
        String line = "";
        Process process = null;
        final long l = System.currentTimeMillis();
        final File executorDirectory = new File(AppsnapConstants.scriptDir);
        if (!executorDirectory.exists()) {
            log.error("executorDirectory does not exist..stopping execution");
            return executorDirectory + "does not exist";
        }
        String shellScript = "appsnap";
        log.info("Shell dir " + AppsnapConstants.scriptDir + shellScript);

        //set defaults
        if (appsnapinterval == null || appsnapinterval == "") {
            appsnapinterval = "2";
        }
        if (appsnapcount == null || appsnapcount == "") {
            appsnapcount = "5";
        }
        ///usr/local/jive/bin/appsnap -i 2 -c 5 > appsnap.out

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", shellScript, "-i", appsnapinterval, "-c", appsnapcount);
            processBuilder.directory(executorDirectory);
            processBuilder.redirectErrorStream(true);
            //processBuilder.redirectOutput(threadDump);     -- JAVA 7 - to be implemented in Jive 7.

            log.debug("About to start the process" + (System.currentTimeMillis() - l));
            process = processBuilder.start();
            /*
            File threadDump = new File(AppsnapConstants.appsnapFilePath);

            if (!threadDump.exists()) {         //Expected to return true coz of  checkIfAppsnapDotOutExists
                log.info("appsnap.out does not exist");
                threadDump.createNewFile();
            }
            */
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            FileWriter fw = new FileWriter(AppsnapConstants.appsnapFilePath + "_" + impl.currentDate() + ".out");
            BufferedWriter bw = new BufferedWriter(fw);

            //log.info("just before the while loop");
            while ((line = br.readLine()) != null) {

                bw.write(line);
                bw.newLine();
            }
            if (bw != null) {

                try {

                    bw.flush();
                    bw.close();
                } catch (Exception ex) {
                    log.error("Cannot close bufferedWriter - could cause memory leak" + ex.getMessage());

                }
            }

            if (fw != null) {
                try {
                    fw.flush();
                    fw.close();

                } catch (Exception ex) {
                    log.error("Cannot close fileWriter - could cause memory leak" + ex.getMessage());
                }
            }

            if (br != null) {
                try {
                    br.close();

                } catch (Exception ex) {
                    log.error("Cannot close bufferedReader - could cause memory leak" + ex.getMessage());


                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    log.error("Cannot close InputStream - could cause memory leak" + ex.getMessage());

                }

            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception ex) {
                    log.warn("Cannot close InputStreamReader - could cause memory leak" + ex.getMessage());


                }
            }


        } catch (IOException ex) {
            log.error("IOException occured in executeAppSnap " + ex.getMessage());
            //  ex.printStackTrace();
        } catch (Exception ex) {
            log.error("Error occured in executeAppSnap " + ex.getStackTrace() + "\n");
            ex.printStackTrace();
        } finally {
            try {
                //  log.info("Going to shell status - very critical");
                int shellExitStatus = process.waitFor();
                log.info("Exist status " + shellExitStatus);
                if (shellExitStatus != 0 || shellExitStatus != 253) {
                    log.info("Shell is not zero, but 253 is good. Status is " + shellScript);
                    //<-- TODO some timeout implementation here

                    //  throw new RuntimeException("Could not run shell script RuntimeException " +scriptDir+shellScript);
                }
            } catch (InterruptedException ex) {
                log.error("Shell Script process was interrupted" + ex.getMessage());
                ex.printStackTrace();
            }
            //log.info("Got here.. good news!");
            try {
                process.getInputStream().close();

            } catch (Exception ex) {
                log.warn("Cannot close process.getInputStream() - could cause memory leak" + ex.getMessage());

            }
            //
            try {
                process.getOutputStream().close();
            } catch (Exception ex) {
                log.warn("Cannot close process.getInputStream() - could cause memory leak" + ex.getMessage());

            }
            try {
                process.getErrorStream().close();
            } catch (Exception ex) {
                log.warn("Cannot close process.getErrorStream() - could cause memory leak" + ex.getMessage());

            }

        }

        return SUCCESS;
    }

    //Currently not in use.. will use this method when it's all good to add some thread dump analysis
    public String getReadDoc() {
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


    public ArrayList getFilelist() {
        ArrayList al = new ArrayList();
        File dir = new File(AppsnapConstants.appsnapDirPath);

        String[] appsnaps = dir.list();
        if (appsnaps == null) {
            //should tell user that no appsnap files exist
        } else {
            al.addAll(Arrays.asList(appsnaps));
            Collections.sort(al);
        }
        return al;
    }


    public Map getMapfiles() {
        ArrayList al = new ArrayList();
        File dir = new File(AppsnapConstants.appsnapDirPath);
        Map<String, Object> data = new HashMap<String, Object>();

        //String[] appsnaps = dir.list();
        for (File file : dir.listFiles()) {

            data.put(dir.getName(), dir.lastModified());

        }
        return data;
    }

    public long getTidyHouseProperty() {
        return JiveGlobals.getJiveLongProperty(AppsnapConstants.TIDY_HOUSE_VALUE_PROPERTY, AppsnapConstants.tidyHouseDefaultValue);
    }

    @Override
    public String execute() {
        //The flow:
        log.info("In the Action class for the RunAppsnapScriptAction plugin! - The flow" + JiveHome.getJiveHomePath());
        // 1. Create appsnap.out dir - if it does not  exist
        impl.createStorageDir();
        log.debug("1.createStorageDir() successfully executed");
        // 2. Check if appsnap.out already exist - take backup of the file if it exist
        //3. Execute the appsnap.sh script
        executeAppSnap();
        log.debug("4. executeAppSnap() successfully executed");

        return SUCCESS;
    }


}
