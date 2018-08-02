package org.framework.adib.core.baseclass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseClass {

    @SuppressWarnings("rawtypes")
    private static AppiumDriver driver;

    // public WebDriverWait wait;

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    @SuppressWarnings("rawtypes")
    public static AppiumDriver getDriver() {
        return driver;
    }

    @SuppressWarnings("rawtypes")
    public static void setDriver(AppiumDriver driver) {
        BaseClass.driver = driver;
    }

    private static AppiumDriverLocalService service;
    private static AppiumServiceBuilder builder;
    private static DesiredCapabilities cap;

    /**
     * Method to initialize the appium driver.
     * 
     * @param: appURL
     *             location of the apk file
     * @param: CONFIG
     *             Config properties of the application under test
     */
    public static void openApplication(String appUrl, Properties CONFIG) throws MalformedURLException {
        File appDir = new File(appUrl);

        File app = new File(appDir, CONFIG.getProperty("APP_NAME"));

        boolean isRunning = checkIfServerIsRunnning(Integer.parseInt(CONFIG.getProperty("APPIUM_PORT")));

        if (!isRunning)
            startServer(CONFIG);

        if (CONFIG.getProperty("OS").equalsIgnoreCase("ANDROID")) {
            // Defining APIUM Capabilities
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, CONFIG.getProperty("DEVICE_NAME"));
            cap.setCapability(MobileCapabilityType.BROWSER_NAME, CONFIG.getProperty("BROWSER_NAME"));
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, CONFIG.getProperty("PLATFORM_VERSION"));
            cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, CONFIG.getProperty("APP_PACKAGE"));
            cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, CONFIG.getProperty("APP_ACTIVITY"));

            // To run with emulator
            cap.setCapability("avd", CONFIG.getProperty("EMU_NAME"));

            // Initializing Android Driver
            String APPIUM_URL = "http://" + CONFIG.getProperty("APPIUM_IP") + ":" + CONFIG.getProperty("APPIUM_PORT")
                    + "/wd/hub";
            driver = new AndroidDriver<AndroidElement>(new URL(APPIUM_URL), cap);

            // Implicit wait definition
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(CONFIG.getProperty("TIME_OUT")),
                    TimeUnit.SECONDS);
        }
    }

    /**
     * Method to start Appium server.
     *
     * @param: CONFIG
     *             the config properties for application under test
     */
    public static void startServer(Properties CONFIG) {
        // Set Capabilities
        cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");

        // Build the Appium service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress(CONFIG.getProperty("APPIUM_IP"));
        builder.usingPort(Integer.parseInt(CONFIG.getProperty("APPIUM_PORT")));
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

        // Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public static boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            // If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public static void stopServer() {
        service.stop();
    }

}
