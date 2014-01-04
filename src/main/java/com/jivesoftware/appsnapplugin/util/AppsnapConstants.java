package com.jivesoftware.appsnapplugin.util;

import com.jivesoftware.community.JiveHome;
    public class AppsnapConstants {
        //public static String appsnapDirPath ="/usr/local/jive/var/appsnaps/";
        public static String appsnapDirPath = JiveHome.getJiveHomePath() + "/appsnaps/";
        public static String appsnapFilePath = appsnapDirPath + "appsnap";
        public static String scriptDir = "/usr/local/jive/bin/";
        public static long tidyHouseDefaultValue = 28;
        public static final String TIDY_HOUSE_VALUE_PROPERTY = "appsnap.file.maxdays";

    }

