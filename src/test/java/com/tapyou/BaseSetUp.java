package com.tapyou;

import org.testng.annotations.BeforeSuite;

public abstract class BaseSetUp {
    public static String SERVER;

    @BeforeSuite
    public void setupServer() {
        serverSetup();
    }

    private void serverSetup() {
        SERVER = System.getProperty("server");
    }
}
