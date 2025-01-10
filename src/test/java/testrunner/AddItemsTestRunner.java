package testrunner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddItemsPage;
import pages.LoginPage;
import setup.DataSet;
import setup.SetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AddItemsTestRunner extends SetUp {

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
        loginPage.doLogin("nusratjui44+1@gmail.com", properties.getProperty("new_pass"));
    }

    private int totalrow=0;
    @Test(dataProvider = "CSVdata",dataProviderClass= DataSet.class,description = "Check if items are added and viewed")
    public void addItems(String name,int n,String tk,String date,String monthVal,String mark) throws InterruptedException {
        driver.findElement(By.className("add-cost-button")).click();
        AddItemsPage addItemsPage=new AddItemsPage(driver);

        addItemsPage.addItem(name,n,tk,date,monthVal,mark);

        Thread.sleep(1000);
        String text=driver.findElements(By.tagName("span")).get(0).getText();
        String[] value=text.split(": ");

        totalrow=Integer.parseInt(value[1]);
    }

    //assertion to check if added items are showing
    @AfterClass
    public void verifyItemVisibility(){
        Assert.assertTrue(totalrow==2);
    }
}
