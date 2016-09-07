package org.tests;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.data.ExcelOperations;
import org.helper.FileUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.pages.HotelAppLoginPage;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class TestBase extends TestListenerAdapter {

    WebDriver driver;
    FileUtilities fileUtilities=new FileUtilities();
    ExcelOperations excelOperations = new ExcelOperations();
   
    
    @BeforeClass
    public void beforeClass() throws IOException {
        //clear all the screenshots
        fileUtilities.deleteAllFilesFromScreenshot();
        // Launch WebDriver
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        Reporter.log("TEST RESULTS");
        Reporter.log("************");
    }
    
    public Object[][] convertToObjectArrayOfHASHTABLE(){
        
        Object obj[][]= new Object[ExcelOperations.rowCount-1][2];
        for(int row=1;row<ExcelOperations.rowCount;row++){
            obj[row-1][0]=(Hashtable<String, String>)ExcelOperations.dataHash[row];
            obj[row-1][1]=(Hashtable<String, String>)ExcelOperations.statusHash[row];
        }
        
        return obj;
    }
    
    public void printTestDetails(Hashtable<String, String> input) {
        
       
        Reporter.log("---------------");
        Reporter.log("TEST CASE ID : ");
        Reporter.log(input.get("TESTCASEID"));
        Reporter.log("TEST CASE DESCRIPTION : ");
        Reporter.log(input.get("TEST_DESCRIPTION"));
        Reporter.log("TEST STATUS : ");
        
      }

    @AfterMethod
    public void afterMethod(){
        Reporter.log("---------------");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        // Launch AUT
        driver.get("http://www.adactin.com/HotelApp");
        
    }

    

    @AfterClass
    public void afterClass() {
        
        driver.quit();
    }
}
