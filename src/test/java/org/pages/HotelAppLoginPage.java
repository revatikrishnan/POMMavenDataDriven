package org.pages;

import java.util.Hashtable;

import org.helper.GlobalImpl;
import org.helper.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class HotelAppLoginPage {
    WebDriver driver;
  
    @FindBy(how=How.ID,using="username")
    WebElement userNameElement;
    
    @FindBy(how=How.ID,using="password")
    WebElement passwordElement;
    
    @FindBy(how=How.ID,using="login")
    WebElement loginLink;
    
    @FindBy(how=How.CSS,using="div.auth_error b")
    WebElement actualErrorElement;
    
    
    public HotelAppLoginPage(WebDriver driver)
    {
        this.driver = driver;
    }
    
    // Enter User Name
    public HotelAppLoginPage enterUserName(String userName)
    {
       userNameElement.sendKeys(userName);
        return this;
    }
    
    // Enter Password
    public HotelAppLoginPage enterPassword(String password)
    {
        passwordElement.sendKeys(password);
        return this;
    }
    
    // Click Login Button
    public HotelAppHomePage clickLoginButton()
    {
        loginLink.click();
        return PageFactory.initElements(driver, HotelAppHomePage.class);
    }
    
    // Perform Login
    public HotelAppHomePage performLogin(String userName, String password)
    {
        return this.enterUserName(userName).enterPassword(password).clickLoginButton();
    }
    
    // Validate UserName is entered correctly
    public boolean validateActualUserName(String expectedUserName)
    {
        String actualUserName = userNameElement.getAttribute("value");
        return actualUserName.equals(expectedUserName);
    }
    
    //vslidate error message for invalid login
    public String getErrorMessageForInvalidLogin(){
        return actualErrorElement.getText();
    }

    public void validateLoginIsNotSuccessful(Hashtable<String, String> input,
            Hashtable<String, String> output, boolean welcomeNote) {
        Assert.assertFalse(welcomeNote, "Login is successful for providing invalid data");
        String actualErrorMessage=this.getErrorMessageForInvalidLogin().trim();
        String expectedErrorMessage=output.get("ERROR_MESSAGE").trim();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage , "Error Message validaion failed");
        if((!welcomeNote) && (actualErrorMessage.equals(expectedErrorMessage))){
            Reporter.log("PASSED");
            GlobalImpl.passedtestcase.add(input.get("TEST_DESCRIPTION"));
        }
        else{
            Reporter.log("FAILED");
            SeleniumUtils.captureScreenShotOnFailure(driver, "verifyLoginNotSuccessfulFAILED_ExpectedErrorMessageNotFound");
            GlobalImpl.failedtestcase.add(input.get("TEST_DESCRIPTION")+";verifyLoginNotSuccessfulFAILED_ExpectedErrorMessageNotFound");
        }
        
      
        
    }
}

