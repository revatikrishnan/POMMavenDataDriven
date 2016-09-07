package org.pages;


import java.util.Hashtable;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.helper.SeleniumUtils;
import org.helper.GlobalImpl;
import org.testng.Assert;
import org.testng.Reporter;

public class HotelAppHomePage {
    WebDriver driver;
    
    public HotelAppHomePage(WebDriver driver)
    {
        this.driver = driver;
    }
    
    @FindBy(how=How.ID,using="username_show")
    WebElement usernameShow;
    
    public boolean validateWelcomeMessageIsDisplayed()
    {
        try {
            WebElement loginMsgElement = loginWelComeMessageElement();
            return loginMsgElement.isDisplayed();
        }
        catch(NoSuchElementException e)
        {
            return false;
        }
    }
    
    public void validateLoginIsSuccessful(Hashtable<String, String> input, Hashtable<String, String> output, boolean welcomeNote){
        
            Assert.assertTrue(welcomeNote, "Login is not successful");
            if(welcomeNote)
                Reporter.log("Welcome Note is displayed succesfully as "+loginWelComeMessageElement().getText());
            else
                Reporter.log("Login is not successful, Welcome note not displayed");
            
            boolean checkWelcome=this.checkLoginWelComeMsgHasCorrectUserName(input.get("USERNAME"));
            Assert.assertTrue(checkWelcome, "Welcome message not as expected");
            if(welcomeNote)
                Reporter.log("Welcome message is as expected "+loginWelComeMessageElement().getText());
            else
                Reporter.log("Login is not successful, Welcome message not as expected");
            
            if(welcomeNote && checkWelcome){
                Reporter.log("PASSED");
                GlobalImpl.passedtestcase.add(input.get("TEST_DESCRIPTION"));
            }
            else{
                Reporter.log("FAILED");
                SeleniumUtils.captureScreenShotOnFailure(driver, "verifyLogin_WelcomeNotORWelcomeMessageNotAsExpected");
                GlobalImpl.failedtestcase.add(input.get("TEST_DESCRIPTION")+";verifyLogin_WelcomeNotORWelcomeMessageNotAsExpected");
            }
        
    }
    private WebElement loginWelComeMessageElement()
    {
        return usernameShow;
    }
    
    public boolean checkLoginWelComeMsgHasCorrectUserName(String expectedUserName)
    {
        String actualWelcomeMsg = loginWelComeMessageElement().getAttribute("value");
        return actualWelcomeMsg.contains(expectedUserName);
    }
}
