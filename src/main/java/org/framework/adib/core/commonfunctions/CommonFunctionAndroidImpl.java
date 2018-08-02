package org.framework.adib.core.commonfunctions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.codehaus.plexus.util.FileUtils;
import org.framework.adib.core.baseclass.BaseClass;

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
            e.printStackTrace();
        }
        System.out.println(result);
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
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        destFile = dateFormat.format(new Date()) + ".png";

        try {
            FileUtils.copyFile(srcFile, new File(screenShot + "/" + destFile));
        } catch (IOException e) {
            System.out.println("Image not transferred to Screenshot folder");
            e.printStackTrace();
        }
        return destFile;
    }

}
