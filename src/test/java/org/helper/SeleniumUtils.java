package org.helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class SeleniumUtils {

    public static void captureScreenShotOnFailure(WebDriver driver,String filename) {

        try {

            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(source, new File(".\\ScreenShots\\" + filename + ".jpg"));

        }

        catch (IOException e) {

            System.out.println(e.getMessage());

        }

    }

}
