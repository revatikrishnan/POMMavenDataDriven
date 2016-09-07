package org.report;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.helper.GlobalImpl;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;
import org.tests.HotelAppTests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
 
public class ExtentReporterNG implements IReporter {
    private ExtentReports extent;
 
    
  
 
   /* private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
 
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName());
              
                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));
                
 
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
 
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable().getMessage());
                }
                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                    //test.log(status, HotelAppTests.log);
                }
 
                extent.endTest(test);
            }
        }
    }*/
    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
       
        int passcount=0;
        int failcount=0;
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                
                if (result.getThrowable() != null) {
                    String testcaseName=GlobalImpl.failedtestcase.get(failcount).split(";")[0];
                    String screenshotName=GlobalImpl.failedtestcase.get(failcount).split(";")[1];
                    test = extent.startTest(testcaseName);
                    failcount=failcount+1;
                    test.log(status, result.getThrowable().getMessage());
                    test.log(status, "Test "+status.toString().toLowerCase() + "ed");
                    test.log(status, "Screenshot added : ");
                    test.log(status, test.addScreenCapture(GlobalImpl.screenshots.getAbsolutePath()+"\\"+screenshotName+".jpg"));
                }
                else{
                    test = extent.startTest(GlobalImpl.passedtestcase.get(passcount));
                    passcount=passcount+1;
                    test.log(status, "Test "+status.toString().toLowerCase() + "ed");
                }
                
                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));
                //test.getTest().startedTime=getTime(result.getStartMillis());
                //test.getTest().endedTime=getTime(result.getEndMillis()));
                
               
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
 
 
                extent.endTest(test);
            }
        }
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();        
    }

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extent = new ExtentReports(outputDirectory + File.separator + "Extent.html", true);
 
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
 
            for (ISuiteResult r : result.values()) {
                
                ITestContext context = r.getTestContext();

                for (String s : Reporter.getOutput()) {
                    extent.setTestRunnerOutput(s);
                }
                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }
 
        extent.flush();
        extent.close();
    }
}

