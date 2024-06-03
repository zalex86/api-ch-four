package com.tapyou;

import com.tapyou.heplers.UserHelper;
import org.testng.annotations.BeforeSuite;

public abstract class BaseSetUp {
    public static String SERVER;
    protected final UserHelper userHelper = new UserHelper();

    @BeforeSuite
    public void setupServer() {
        serverSetup();
    }

    private void serverSetup() {
        SERVER = System.getProperty("server");
    }
}
