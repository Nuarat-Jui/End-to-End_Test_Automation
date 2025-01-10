package testrunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NewPassPage;
import pages.ResetLinkPage;
import setup.SetUp;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ResetLinkTestRunner extends SetUp {

    @Test(priority = 1,description = "Try to reset password with non-registered email")
    public void doResetWithNonRegisteredMail(){
        driver.findElement(By.partialLinkText("Reset")).click();
        ResetLinkPage reset=new ResetLinkPage(driver);
        reset.doReset("kia@gmail.com");
        String msgAc=driver.findElement(By.tagName("p")).getText();
        String msgEx="not registered";
        Assert.assertTrue(msgAc.contains(msgEx));
        driver.navigate().refresh();
    }
    @Test(priority = 2,description = "Try to reset password with invalid email without @ sign")
    public void doResetWithInvalidMail(){
        ResetLinkPage reset=new ResetLinkPage(driver);
        reset.doReset("kiagmail.com");
        WebElement email=driver.findElement(By.className("MuiInputBase-input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String msgAc = (String) js.executeScript("return arguments[0].validationMessage;",email);
        String msgEx="include an '@'";
        Assert.assertTrue(msgAc.contains(msgEx));
        driver.navigate().refresh();
    }
    @Test(priority = 3,description = "Try to reset password with registered email")
    public void doResetWithValidMail() throws ConfigurationException, IOException, InterruptedException {
        ResetLinkPage reset=new ResetLinkPage(driver);
        reset.doReset("nusratjui44+1@gmail.com");
        String msgAc=driver.findElement(By.tagName("p")).getText();
        String msgEx="link sent to your email";
        Assert.assertTrue(msgAc.contains(msgEx));
        Thread.sleep(10000);
        readmail();
    }
    @Test(priority = 4, description = "Change to new password")
    public void ChangePass() throws IOException, ConfigurationException {
        getEnvVar();
        driver.get(properties.getProperty("link"));
        NewPassPage newPass=new NewPassPage(driver);
        int password=Utils.generateRandom(99999999,10000000);
        String pass= String.valueOf(password);
        System.out.println(pass);
        newPass.ChangePas(pass);
        Utils.setEnvVar("new_pass",pass);
    }


    Properties properties;
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }
    public String gmailList() throws IOException, ConfigurationException {
        getEnvVar();
        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response response = given().contentType("application/json").header("Authorization", "Bearer " + properties.getProperty("gmail_token")).when().get("/gmail/v1/users/me/messages");
        System.out.println(response.asString());
        JsonPath jsonPath = response.jsonPath();
        String msgID = jsonPath.get("messages[0].id");
        return msgID;
    }
    public void readmail() throws IOException, ConfigurationException {
        String msgID=gmailList();
        getEnvVar();
        RestAssured.baseURI="https://gmail.googleapis.com";
        Response response=given().contentType("application/json").header("Authorization","Bearer "+properties.getProperty("gmail_token")).when().get("/gmail/v1/users/me/messages/"+msgID);

        JsonPath jsonPath = response.jsonPath();
        String snippet = jsonPath.get("snippet");
        //System.out.println(snippet);
        String[] parts = snippet.split("password:");
        if (parts.length > 1) {
            String link = parts[1].trim();
            System.out.println(link);
            Utils.setEnvVar("link",link);
        }
    }

}
