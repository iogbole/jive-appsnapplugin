package com.jivesoftware.appsnapplugin;

import com.jivesoftware.base.plugin.Plugin;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: israel.ogbole
 * Date: 10/4/13
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppsnapPlugin implements Plugin {
    //Initialize the logger so you can create some logging level messages!
    Logger log = Logger.getLogger(AppsnapPlugin.class);


    /**
     * Method that is called when the application starts up with the plugin.
     */
    public void initPlugin() {
        log.info("Starting the AppsnapPlugin Plugin...");
    }


    public void destroy() {
        log.info("Stopping the AppsnapPlugin Plugin...");
    }
}

