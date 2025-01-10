package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegisterPage {
    @FindBy(id="firstName")
    WebElement txtFirstname;

    @FindBy(id="email")
    WebElement txtEmail;

    @FindBy(id="password")
    WebElement txtPass;

    @FindBy(id="phoneNumber")
    WebElement txtPhone;

    @FindBy(css="[name=gender]")
    List<WebElement> btnRadioGender;

    @FindBy(css="[type=checkbox]")
    WebElement checkboxTerms;

    @FindBy(id="register")
    WebElement btnregister;

    public RegisterPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void doRegister(String fristname,String email,String pass,String phone){
        txtFirstname.sendKeys(fristname);
        txtEmail.sendKeys(email);
        txtPass.sendKeys(pass);
        txtPhone.sendKeys(phone);
        btnRadioGender.get(1).click();
        checkboxTerms.click();
        btnregister.click();
    }

}
