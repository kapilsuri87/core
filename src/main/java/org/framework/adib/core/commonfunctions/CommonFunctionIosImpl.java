package org.framework.adib.core.commonfunctions;

import java.time.Duration;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import org.framework.adib.core.utilities.Log;

public class CommonFunctionIosImpl implements CommonFunction {

    /** The driver. */
    IOSDriver<IOSElement> driver;

    /**
     * Instantiates a new common function android impl.
     *
     * @param driver
     *            the driver
     */
    public CommonFunctionIosImpl(IOSDriver<IOSElement> driver) {
        this.driver = driver;
    }

    @Override
    public void hideKeyBoard() {
        driver.hideKeyboard();
    }

    @Override
    public String readToastMsg() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String takeScreenshots() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to wait the tread.
     *
     * @param duration
     *            wait time in long
     */
    public void driverWait(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Log.info("Thread.wait failed to execute");
        }
    }
    
    /**
     * This method is used to enable a switch
     * 
     * @param element
     *            MobileElement of the switch
     * @param status
     *            The expected value which must be set
     */
    public void enableSwitch(MobileElement element, Boolean status) {
        if (status) {
            if (element.getAttribute("value").equalsIgnoreCase("0")) {
                Log.info("Standard switch is disabled");
                element.click();
            }
        } else {
            if (element.getAttribute("value").equalsIgnoreCase("1")) {
                Log.info("Standard switch is enabled");
                element.click();
            }
        }
    }

    @Override
    public void minimizeApplication() {
        driver.runAppInBackground(Duration.ofSeconds(-1));
        driverWait(2000);

    }

    @Override
    public void selectMinimizedApplication(String locator) {
        // TODO Auto-generated method stub

    }

    @Override
    public void switchApplication(String settingsAppPackageName, String settingsAppActivityName) {
        driver.launchApp();

    }

    @Override
    public void dismissAlert() {
        // TODO Auto-generated method stub

    }

}
