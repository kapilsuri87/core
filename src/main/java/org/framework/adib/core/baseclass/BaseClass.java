package org.framework.adib.core.baseclass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseClass {

    private static AndroidDriver<AndroidElement> androiddriver;
    
    private static IOSDriver<IOSElement> iosdriver;

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public static AndroidDriver<AndroidElement> getAndroidDriver() {
        return androiddriver;
    }
    
    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public static IOSDriver<IOSElement> getIosDriver() {
        return iosdriver;
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
    public static void openApplication(String appUrl, Properties config) throws MalformedURLException {
        File appDir = new File(appUrl);

        File app = new File(appDir, config.getProperty("APP_NAME"));

        // defining appium url
        String appiumUrl = "http://" + config.getProperty("APPIUM_IP") + ":" + config.getProperty("APPIUM_PORT") + "/wd/hub";

        
        boolean isRunning = checkIfServerIsRunnning(Integer.parseInt(config.getProperty("APPIUM_PORT")));
          
        if (!isRunning) { startServer(config); }
         
        switch (config.getProperty("OS")) {
            default:
                //Log.info("OS version is not correct");
                break;
            case "ANDROID": {
                // Defining APIUM Capabilities
                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("PLATFORM_VERSION"));
                cap.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("EMU_NAME"));
                cap.setCapability(MobileCapabilityType.BROWSER_NAME, config.getProperty("BROWSER_NAME"));
                cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.getProperty("APP_PACKAGE"));
                cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, config.getProperty("APP_ACTIVITY"));
                cap.setCapability(MobileCapabilityType.FULL_RESET, false);
                cap.setCapability(MobileCapabilityType.NO_RESET, false);
                cap.setCapability(MobileCapabilityType.UDID, config.getProperty("EMU_NAME"));
//                if(config.getProperty("IS_PHYSICAL").equalsIgnoreCase("FALSE"))
//                {
//                  //To run with emulator
//                    cap.setCapability(MobileCapabilityType.u"avd", config.getProperty("EMU_NAME"));
//                    
//                }                
                androiddriver=new AndroidDriver<AndroidElement>(new URL(appiumUrl), cap);
    
                // Implicit wait definition
                androiddriver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("TIME_OUT")),
                        TimeUnit.SECONDS);
                
                System.out.println("The session ID for "+config.getProperty("EMU_NAME")+" is : "
                        + ""+androiddriver.getSessionId());
                break;
            }
            case "IOS": {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("PLATFORM_VERSION"));
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("EMU_NAME"));
                caps.setCapability(MobileCapabilityType.BROWSER_NAME, config.getProperty("IOS_BROWSER_NAME"));
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                // caps.setCapability("bundleid", "com.example.apple-samplecode.UICatalog");
                caps.setCapability(MobileCapabilityType.NO_RESET, config.getProperty("RESET_VALUE"));
                iosdriver = new IOSDriver<IOSElement>(new URL(appiumUrl), caps);
                break;
            }
        }
    }

    /**
     * Method to start Appium server.
     *
     * @param: CONFIG
     *             the config properties for application under test
     */
    public static void startServer(Properties config) {
        // Set Capabilities
        cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");

        // Build the Appium service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress(config.getProperty("APPIUM_IP"));
        builder.usingPort(Integer.parseInt(config.getProperty("APPIUM_PORT")));
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, config.getProperty("BOOTSTRAP"));

        // Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }
    

    /**
     * Method to check if Appium server.
     *
     * @param: port
     *             the port number on which appium is running
     */

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
