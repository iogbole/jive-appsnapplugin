package com.jivesoftware.appsnapplugin.util;

import com.jivesoftware.community.JiveHome;

/**
 * Created with IntelliJ IDEA.
 * User: israel.ogbole
 * Date: 10/8/13
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppsnapConstants {

    public static String appsnapDirPath = JiveHome.getJiveHomePath() + "/plugins/appsnapplugin/appsnaps/";
    // public static String appsnapDirPath= "/Users/israel.ogbole/jive/plugins/appsnapplugin/appsnaps/";
    public static String appsnapFilePath = appsnapDirPath + "appsnap";
    public static String scriptDir = "/usr/local/jive/bin/";
    public static long tidyHouseDefaultValue = 28;
    public static final String TIDY_HOUSE_VALUE_PROPERTY = "appsnap.file.maxdays";

}
