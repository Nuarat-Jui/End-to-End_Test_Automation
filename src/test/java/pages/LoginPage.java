package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    @FindBy(id="email")
    WebElement txtemail;

    @FindBy(id="password")
    WebElement txtpass;

    @FindBy(css="[type=submit]")
    WebElement btnsubmit;

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void doLogin(String email,String pass){
        txtemail.sendKeys(email);
        txtpass.sendKeys(pass);
        btnsubmit.click();
    }
    public void logout(){
        UpdateGmailPage updateGmailPage=new UpdateGmailPage(driver);

        updateGmailPage.btn.get(0).click();
        updateGmailPage.menu.get(1).click();
    }


}
