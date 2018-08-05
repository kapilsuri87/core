package org.framework.adib.core.commonfunctions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.codehaus.plexus.util.FileUtils;
import org.framework.adib.core.baseclass.BaseClass;
import org.framework.adib.core.utilities.Log;
import org.openqa.selenium.OutputType;

// TODO: Auto-generated Javadoc
/**
 * @author kapilsuri
 *
 */
public class CommonFunctionAndroidImpl extends BaseClass implements CommonFunction {
    
    /** The driver. */
    AndroidDriver<AndroidElement> driver;
    
    /**
     * Instantiates a new common function android impl.
     *
     * @param driver
     *            the driver
     */
    public CommonFunctionAndroidImpl(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }
    
    /** The screen shot. */
    static String screenShot = "screenshots";
    
    /** The dest file. */
    String destFile;
    
    /** The src file. */
    File srcFile;
    
    /** The scr shot dir. */
    File scrShotDir = new File("./" + screenShot + "//");
    
    /**
     * Method to hide active keyboard.
     *
     */
    public void hideKeyBoard() {
        driver.hideKeyboard();
    }
    
    /**
     * Method to wait the tread.
     *
     * @param t
     *            wait time in long
     */
    public void driverWait(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            Log.info("Thread.wait failed to execute");
        }
    }
    
    /**
     * Method to read a Toast message.
     *
     */
    public String readToastMsg() {
        String imageScrShot = takeScreenshots();
        String result = null;
        File imagefile = new File(scrShotDir, imageScrShot);
        ITesseract instance = new Tesseract();
        
        File testDataFolder = net.sourceforge.tess4j.util.LoadLibs.extractTessResources("tessdata");
        
        instance.setDatapath(testDataFolder.getAbsolutePath());
        try {
            result = instance.doOCR(imagefile);
        } catch (TesseractException e) {
            Log.info(e.getMessage());
            Log.info(e.toString());
        }
        Log.info(result);
        return result;
    }
    
    /**
     * Method to take a screenshot.
     *
     */
    public String takeScreenshots() {
        srcFile = driver.getScreenshotAs(OutputType.FILE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy__hh-mm-ss");
        try {
            new File(screenShot).mkdir();
        } catch (Exception e) {
            Log.info(e.getMessage());
            Log.info(e.toString());
        }
        
        destFile = dateFormat.format(new Date()) + ".png";
        
        try {
            FileUtils.copyFile(srcFile, new File(screenShot + "/" + destFile));
        } catch (IOException e) {
            Log.info(e.getMessage());
            Log.info("Image not transferred to Screenshot folder");
            Log.info(e.toString());
        }
        return destFile;
    }
    
    /**
     * Method to minimize the application on screen using Home button.
     *
     */
    public void minimizeApplication() {
        
        driver.pressKeyCode(AndroidKeyCode.HOME);
        driverWait(2000);
    }
    
    /**
     * Method to minimize the screen.
     *
     * @param: String
     *             locator locator value in ID format
     */
    
    public void selectMinimizedApplication(String locator) {
        driver.pressKeyCode(187);
        driver.findElementById(locator).click();
        driverWait(2000);
    }
    
}
