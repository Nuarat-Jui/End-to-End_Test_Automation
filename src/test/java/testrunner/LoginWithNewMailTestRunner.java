package testrunner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import setup.SetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginWithNewMailTestRunner extends SetUp {

    Properties properties;
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }
    @Test(priority = 1,description = "Verify login is unsuccessful while using old email")
    public void doLoginWithOldGmail() throws IOException {
        getEnvVar();
        LoginPage loginPage=new LoginPage(driver);
        loginPage.doLogin("nusratjui44+1@gmail.com", properties.getProperty("new_pass") );
        String headertxtAC=driver.findElement(By.tagName("p")).getText();
        String headertxtEX="Invalid email or password";
        Assert.assertTrue(headertxtAC.contains(headertxtEX));
        driver.navigate().refresh();
    }
    @Test(priority = 2,description = "Verify login is successful using new email")
    public void doLoginWithNewGmail() throws IOException {
        getEnvVar();
        LoginPage loginPage=new LoginPage(driver);
        loginPage.doLogin(properties.getProperty("new_mail"), properties.getProperty("new_pass") );
        String headertxtAC=driver.findElement(By.tagName("h2")).getText();
        String headertxtEX="User Daily Costs";
        Assert.assertTrue(headertxtAC.contains(headertxtEX));
        loginPage.logout();
    }

    @Test(priority = 3,description = "Admin login with secure credentials")
    public void adminLogin(){
        LoginPage loginpage=new LoginPage(driver);
        if(System.getProperty("email")!=null && System.getProperty("pass")!=null){
            loginpage.doLogin(System.getProperty("email"),System.getProperty("pass"));
        }
        else{
            loginpage.doLogin("admin@test.com","admin123");
        }
        String headertxtAC=driver.findElement(By.tagName("h2")).getText();
        String headertxtEX="Admin Dashboard";
        Assert.assertTrue(headertxtAC.contains(headertxtEX));
        Assert.assertTrue(driver.findElement(By.className("total-count")).isDisplayed());
    }
}
