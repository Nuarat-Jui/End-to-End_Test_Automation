package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NewPassPage {
    @FindBy(css = "[type=password]")
    List<WebElement> pass;

    @FindBy(css="[type=submit]")
    WebElement btnreset;
    public NewPassPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void ChangePas(String password){
         pass.get(0).sendKeys(password);
         pass.get(1).sendKeys(password);
         btnreset.click();
    }
}
