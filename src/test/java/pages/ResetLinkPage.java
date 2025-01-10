package pages;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ResetLinkPage {
    @FindBy(className = "MuiInputBase-input")
    WebElement email;

    @FindBy(css="[type=submit]")
    WebElement btnreset;
    public ResetLinkPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void doReset(String mail){
        email.sendKeys(mail);
        btnreset.click();
    }
}
