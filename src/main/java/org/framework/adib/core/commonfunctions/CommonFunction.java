package org.framework.adib.core.commonfunctions;

import io.appium.java_client.MobileElement;

public interface CommonFunction {

    void hideKeyBoard();

    String readToastMsg();

    String takeScreenshots();

    void minimizeApplication();

    void driverWait(long durarion);

    void selectMinimizedApplication(String locator);

    void switchApplication(String settingsAppPackageName, String settingsAppActivityName);

    void dismissAlert();
   
    void enableSwitch(MobileElement element, Boolean status);

}
