package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddItemsPage {
    @FindBy(id = "itemName")
    WebElement itemName;

    @FindBy(css = "[type=button]")
    List<WebElement> btnadditem;

    @FindBy(css = "[type=number]")
    List<WebElement> amount;

    @FindBy(id = "purchaseDate")
    WebElement purchasedate;

    @FindBy(id = "month")
    WebElement month;

    @FindBy(id="remarks")
    WebElement remark;

    @FindBy(className = "submit-button")
    WebElement btnsubmit;

    WebDriver driver;
    public AddItemsPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public void addItem(String name,int n,String tk,String date,String monthVal,String mark){
        itemName.sendKeys(name);

        if(n!=0){
            for (int i = 1; i < n; i++) {
                btnadditem.get(2).click();
            }
        }
        amount.get(1).sendKeys(tk);
        if(!date.equals(" ")){
            String[] pd=date.split("-");
            purchasedate.sendKeys(pd[0]);
            purchasedate.sendKeys(pd[1]);
            purchasedate.sendKeys(Keys.ARROW_RIGHT);
            purchasedate.sendKeys(pd[2]);
        }

        if(!monthVal.equals(" ")){
            Select options=new Select(month);
            options.selectByValue(monthVal);
        }

        if(!mark.isBlank()){
            remark.sendKeys(mark);
        }
        btnsubmit.click();

        WebDriverWait Alertwait=new WebDriverWait(driver, Duration.ofSeconds(10));
        Alertwait.until(ExpectedConditions.alertIsPresent());

        Alert alert=driver.switchTo().alert();
        alert.accept();
    }

}

