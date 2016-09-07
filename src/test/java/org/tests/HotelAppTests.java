package org.tests;


import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.data.ExcelOperations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.pages.HotelAppHomePage;
import org.pages.HotelAppLoginPage;

public class HotelAppTests extends TestBase{
    
    HotelAppLoginPage hotelAppLoginPage;
    
    String excelPath=System.getProperty("user.dir").replaceAll("target", "")+"\\TestData\\TestData.xls";
    String sheetname="TestLogin";
    String testCaseId="TCLogin";
    
    @DataProvider(name="testLoginData")
    public Object[][] readFromExcel(){
        ExcelOperations.reset();
        
        ExcelOperations.readData(excelPath, sheetname, testCaseId);
        
        return convertToObjectArrayOfHASHTABLE();
        /*Object obj[][]=new Object[ExcelOperations.rowCount-1][2];
        for(int row=1;row<ExcelOperations.rowCount;row++){
            obj[row-1][0]=(Hashtable<String, String>)ExcelOperations.dataHash[row];
            obj[row-1][1]=(Hashtable<String, String>)ExcelOperations.statusHash[row];
        }
        return obj;  */  
        
    }
    
    
    @Test(dataProvider = "testLoginData")
    public void testLogins(Hashtable<String, String> input,Hashtable<String, String> output){
        printTestDetails(input);
        hotelAppLoginPage=PageFactory.initElements(driver, HotelAppLoginPage.class);
        HotelAppHomePage hoteAppHomePage=hotelAppLoginPage.performLogin(input.get("USERNAME"), input.get("PASSWORD"));
        boolean welcomeNote=hoteAppHomePage.validateWelcomeMessageIsDisplayed();
        if(output.get("EXPECTED").equalsIgnoreCase("success"))
            hoteAppHomePage.validateLoginIsSuccessful(input,output,welcomeNote);
        else
            hotelAppLoginPage.validateLoginIsNotSuccessful(input,output,welcomeNote);
    }
  
  
  


}

