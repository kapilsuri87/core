package org.framework.adib.core.commonfunctions;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.time.Duration;

import org.framework.adib.core.utilities.Log;

public class CommonFunctionIOSImpl implements CommonFunction {

    /** The driver. */
    IOSDriver<IOSElement> driver;

    /**
     * Instantiates a new common function android impl.
     *
     * @param driver
     *            the driver
     */
    public CommonFunctionIOSImpl(IOSDriver<IOSElement> driver) {
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
     * @param t
     *            wait time in long
     */
    public void driverWait(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Log.info("Thread.wait failed to execute");
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
