package testrunner;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
//import setup.EmailMessage;
import setup.SetUp;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class RegistrationTestRunner extends SetUp {
    @Test(priority = 1, description = "User registration with mandatory inputs")
    public void Register() throws ConfigurationException, IOException, InterruptedException {
        driver.findElement(By.partialLinkText("Register")).click();

        RegisterPage registerPage=new RegisterPage(driver);
        Faker faker=new Faker();
        String firstname=faker.name().firstName();
        String email="nusratjui44+1@gmail.com";
        String pass="1234";
        String phone= "019"+ Utils.generateRandom(10000000,99999999);
        registerPage.doRegister(firstname,email,pass,phone);

        Thread.sleep(2000);

        String alertmsgAc=driver.findElement(By.className("Toastify__toast")).getText();//to get toast alert msg(fixed)
        String alertmsgEx="registered successfully!";
        Assert.assertTrue(alertmsgAc.contains(alertmsgEx));
        Thread.sleep(5000);
        String msgAc= readmail();
        String msgEx="Congratulations";
        Assert.assertTrue(msgAc.contains(msgEx));

    }
    Properties properties;//globally declare
    public void getEnvVar() throws IOException {
        properties=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        properties.load(fs);
    }
    public String gmailList() throws IOException, ConfigurationException {
        getEnvVar();
        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response response = given().contentType("application/json").header("Authorization", "Bearer " + properties.getProperty("gmail_token")).when().get("/gmail/v1/users/me/messages");
        //System.out.println(response.asString());
        JsonPath jsonPath = response.jsonPath();
        String msgID = jsonPath.get("messages[0].id");
        return msgID;
    }
    public String readmail() throws IOException, ConfigurationException {
        String msgID=gmailList();
        getEnvVar();
        RestAssured.baseURI="https://gmail.googleapis.com";
        Response response=given().contentType("application/json").header("Authorization","Bearer "+properties.getProperty("gmail_token")).when().get("/gmail/v1/users/me/messages/"+msgID);

        JsonPath jsonPath = response.jsonPath();
        String subject = jsonPath.getString("payload.headers.find{it.name=='Subject'}.value");
        System.out.println(subject);
        return subject;

        }

}
