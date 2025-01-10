package testrunner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import setup.SetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginWithNewPassTestRunner extends SetUp {

    Properties properties;//globally declare
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }

    @Test(description = "Login with new password")
    public void doLogin() throws IOException {
        getEnvVar();
        LoginPage loginPage=new LoginPage(driver);
        loginPage.doLogin("nusratjui44+1@gmail.com", properties.getProperty("new_pass"));
        String headertxtAC=driver.findElement(By.tagName("h2")).getText();
        String headertxtEX="User Daily Costs";
        Assert.assertTrue(headertxtAC.contains(headertxtEX));

    }
}
