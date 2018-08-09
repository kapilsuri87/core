package org.framework.adib.core.commonfunctions;

public interface CommonFunction {
    
    void hideKeyBoard();
    
    String readToastMsg();
    
    String takeScreenshots();
    
    void minimizeApplication();
    
    void driverWait(long t);
    
    void selectMinimizedApplication(String locator);
    
    void switchApplication(String settingsAppPackageName, String settingsAppActivityName);
    
    void dismissAlert();
    
}
