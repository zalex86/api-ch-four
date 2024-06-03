package com.tapyou.tests;

import com.tapyou.BaseSetUp;
import org.testng.annotations.Test;

public class UsersTests extends BaseSetUp {
    @Test(description = "Simple test")
    public void simpleCheck() {
        System.out.println("check work");
        System.out.println(SERVER);
    }

}
