package org.helper;

import java.io.File;
import java.util.ArrayList;

public class GlobalImpl {

    public static ArrayList<String> passedtestcase=new ArrayList<String>();
    public static ArrayList<String> failedtestcase=new ArrayList<String>();
    public static ArrayList<String> skippedtestcase=new ArrayList<String>();
    public static File screenshots=new File(System.getProperty("user.dir").replace("target", "")+"/ScreenShots");}
