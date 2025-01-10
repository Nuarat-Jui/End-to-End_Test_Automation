package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdminDashboardPage {

      @FindBy(className = "search-box")
      WebElement search;

      @FindBy(tagName = "td")//2
      public List<WebElement> tableValue;

      public AdminDashboardPage(WebDriver driver){
          PageFactory.initElements(driver,this);
      }
      public void searchByGmail(String mail){
          search.sendKeys(mail);
      }
}
