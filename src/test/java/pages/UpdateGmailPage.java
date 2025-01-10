package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UpdateGmailPage {

    @FindBy(css="[type=button]")
    public List<WebElement> btn;

    @FindBy(css="[role=menuitem]")
    public List<WebElement> menu;

    @FindBy(css="[type=email]")
    WebElement txtemail;

    public UpdateGmailPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void updateGmail(String newemail){
        btn.get(1).click();
        txtemail.sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);
        txtemail.sendKeys(newemail);
        btn.get(2).click();
    }

}
