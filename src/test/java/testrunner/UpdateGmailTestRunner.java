package testrunner;

import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UpdateGmailPage;
import setup.SetUp;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class UpdateGmailTestRunner extends SetUp {

    Properties properties;
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }
    @BeforeTest
    public void doLogin() throws IOException {
        getEnvVar();
        LoginPage loginPage=new LoginPage(driver);
        loginPage.doLogin("nusratjui44+1@gmail.com",properties.getProperty("new_pass"));

    }
    @Test(description = "Update existing gmail with new gmail")
    public void UpdateGmail() throws ConfigurationException {
        UpdateGmailPage updateGmailPage=new UpdateGmailPage(driver);
        updateGmailPage.btn.get(0).click();
        updateGmailPage.menu.get(0).click();

        Faker faker=new Faker();
        String newemail=faker.name().firstName().toLowerCase()+"@gmail.com";
        updateGmailPage.updateGmail(newemail);
        Utils.setEnvVar("new_mail",newemail);

        WebDriverWait Alertwait=new WebDriverWait(driver, Duration.ofSeconds(10));
        Alertwait.until(ExpectedConditions.alertIsPresent());

        Alert alert=driver.switchTo().alert();
        String txtalertAC=alert.getText();
        String txtalertEX="User updated successfully!";
        Assert.assertTrue(txtalertAC.contains(txtalertEX));
        alert.accept();

        LoginPage loginPage=new LoginPage(driver);
        loginPage.logout();

    }


}
